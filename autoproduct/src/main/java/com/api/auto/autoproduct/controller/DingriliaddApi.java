import com.alibaba.fastjson2.JSONObject;
import com.api.auto.autoproduct.util.HttpRequestUtils;
import com.api.auto.autoproduct.result.ErrorResultResponse;
import com.api.auto.autoproduct.util.ApiUtil;
import com.api.auto.autoproduct.util.AppUtil;
import org.springframework.util.StringUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

/**
* @createDate:Tue Sep 19 14:31:55 CST 2023
* @description:用于添加浙大钉个人日历项目
* */
public class DingriliaddApi{

    public static JSONObject dingriliadd(String appid, String xgh, String title, String description, String addr, Date start, Date end, String attendees, String remind, String cid){
        try {

            // 必填参数为空处理
            if (!StringUtils.hasText(appid)) {
                return ErrorResultResponse.PARAMS_EXCEPTION_RESPONSE;
            }
            if (!StringUtils.hasText(xgh)) {
                return ErrorResultResponse.PARAMS_EXCEPTION_RESPONSE;
            }
            if (!StringUtils.hasText(title)) {
                return ErrorResultResponse.PARAMS_EXCEPTION_RESPONSE;
            }
            if (!StringUtils.hasText(description)) {
                return ErrorResultResponse.PARAMS_EXCEPTION_RESPONSE;
            }
            if (!StringUtils.hasText(start.toString())) {
                return ErrorResultResponse.PARAMS_EXCEPTION_RESPONSE;
            }
            if (!StringUtils.hasText(end.toString())) {
                return ErrorResultResponse.PARAMS_EXCEPTION_RESPONSE;
            }

            JSONObject reqJSON = new JSONObject();
            JSONObject parameter = new JSONObject();

            // 所有参数不为空处理
            if (StringUtils.hasText(appid)) {
                parameter.put("appid", appid);
            }
            if (StringUtils.hasText(xgh)) {
                parameter.put("xgh",xgh);
            }
            if (StringUtils.hasText(title)) {
                parameter.put("title",title);
            }
            if (StringUtils.hasText(description)) {
                parameter.put("description",description);
            }
            if (StringUtils.hasText(addr)) {
                parameter.put("addr",addr);
            }
            if (StringUtils.hasText(start.toString())) {
                parameter.put("start",start);
            }
            if (StringUtils.hasText(end.toString())) {
                parameter.put("end",end);
            }
            if (StringUtils.hasText(attendees)) {
                parameter.put("attendees",attendees);
            }
            if (StringUtils.hasText(remind)) {
                parameter.put("remind",remind);
            }
            if (StringUtils.hasText(cid)) {
                parameter.put("cid",cid);
            }

            // 获取token并判断是否有值
            String token = AppUtil.getToken(appid);
            if (!StringUtils.hasText(token)) {
                return ErrorResultResponse.TOKEN_EXCEPTION_RESPONSE;
            }

            // 封装参数
            reqJSON.put("token", token);
            reqJSON.put("parameter", parameter);

            // 获取秘钥
            String secretKey = AppUtil.getSecret(appid);
            if(!StringUtils.hasText(secretKey)) {
                return ErrorResultResponse.SECRET_KEY_EXCEPTION_RESPONSE;
            }

            //获取加密后的code和签名signature
            String code = ApiUtil.getCode(reqJSON.toString());
            String signature = ApiUtil.getSignature(code, secretKey);
            Map<String,String> map = new HashMap<>();
            map.put("signature", signature);
            map.put("code", code);

            // http获取最后的返回结果
            String result = HttpRequestUtils.doPost("https://api.zju.edu.cn/api/dingriliadd/service",map,"utf-8");
            JSONObject jsonResponse = JSONObject.parseObject(result);

            // 判断是否成功获取信息
            if ("success".equals(jsonResponse.getString("result"))) {
                // 操作成功，单条数据放在data.entity,多条数据，放在data.list
                return jsonResponse;
            } else {
                return jsonResponse;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorResultResponse.INTERNAL_SERVER_EXCEPTION_RESPONSE;
          }
    }
}
