package com.api.auto.autoproduct.util;

import com.alibaba.fastjson2.JSONObject;
import com.api.auto.autoproduct.result.ApiInfo;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class ApiUtil {


    static final String ALGORITHM = "HmacSHA256";
    static final String URL = "https://api.zju.edu.cn/DoAction.action";

    /**
     * 首字母大写(进行字母的ascii编码前移，效率是最高的)
     *
     * @param fieldName 需要转化的字符串
     */
    public static String getFirstCharUpper(String fieldName) {
        char[] chars = fieldName.toCharArray();
        chars[0] = toUpperCase(chars[0]);
        return String.valueOf(chars);
    }

    /**
     * 字符转成大写
     *
     * @param c 需要转化的字符
     */
    public static char toUpperCase(char c) {
        if (97 <= c && c <= 122) {
            c ^= 32;
        }
        return c;
    }

    public static String getCode(String paramsJsonString) {
        String code = (new BASE64Encoder()).encode(paramsJsonString.getBytes(StandardCharsets.UTF_8));
        return code;
    }

    public static String getSignature(String code, String key) throws Exception {

        // 获得signature参考java代码如下:
        Mac sha256_HMAC = Mac.getInstance(ALGORITHM);
        if (!StringUtils.hasText(key)) {
            return null;
        }
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        sha256_HMAC.init(secretKey);
        String signature = (new BASE64Encoder()).encode(sha256_HMAC.doFinal(code.getBytes(StandardCharsets.UTF_8)));
        return signature;
    }


    public static ArrayList<List> getApiList(String type) throws IOException {

        // 获取对应菜单type下的接口列表
        ArrayList<List> apiList = null;
        JSONObject apiListRequest = new JSONObject();
        JSONObject parameter = new JSONObject();

        apiListRequest.put("action", "ApiHomeService");
        apiListRequest.put("operation", "apilist");
        apiListRequest.put("dest", "MDCDataApiType");
        // type
        if (!StringUtils.hasText(type)) {
            return null;
        }
        parameter.put("type", type);

        apiListRequest.put("parameter", parameter);
        String result = HttpRequestUtils.okHttpRequest(URL, apiListRequest.toString());

        JSONObject parseObject = JSONObject.parseObject(result);
        if ("success".equals(parseObject.getString("result"))) {
            // 得到api接口信息的列表
            ApiInfo apiInfo = JSONObject.parseObject(parseObject.get("data").toString(), ApiInfo.class);
            if(apiInfo==null){
                return apiList;
            }
            apiList = apiInfo.getList();
        }
        return apiList;
        /*
        [["649ce81ff543f522a78b","dinguserinfo","3001","个人详细信息"], ["649ce887f58af522a78b","dinguserid","3001","获取userid"],
       */
    }

    public static String createApiDocRequestString(String apiId) {

        //获取接口文档中的输入参数和输出参数及api信息

        JSONObject apiDocRequest = new JSONObject();
        JSONObject parameter = new JSONObject();
        apiDocRequest.put("action", "ApiHomeService");
        apiDocRequest.put("dest", "MDCDataApiDoc");
        apiDocRequest.put("operation", "apidoclist");
        //各个接口的id参数动态化
        if (StringUtils.hasText(apiId)) {
            parameter.put("apiid", apiId);
        } else {
            return apiId;
        }
        apiDocRequest.put("parameter", parameter);
        return apiDocRequest.toString();

    }


}
