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
* @createDate:Tue Sep 19 14:31:56 CST 2023
* @description:用于添加浙大钉日历项目参与人
* */
public class DingriliusraddApi{

    public static JSONObject dingriliusradd(String appid, String xgh, String id, String attendees, String must){
        try {

            // 必填参数为空处理
            if (!StringUtils.hasText(appid)) {
                return ErrorResultResponse.PARAMS_EXCEPTION_RESPONSE;
            }
            if (!StringUtils.hasText(xgh)) {
                return ErrorResultResponse.PARAMS_EXCEPTION_RESPONSE;
            }
            if (!StringUtils.hasText(id)) {
                return ErrorResultResponse.PARAMS_EXCEPTION_RESPONSE;
            }
            if (!StringUtils.hasText(attendees)) {
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
            if (StringUtils.hasText(id)) {
                parameter.put("id",id);
            }
            if (StringUtils.hasText(attendees)) {
                parameter.put("attendees",attendees);
            }
            if (StringUtils.hasText(must)) {
                parameter.put("must",must);
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
            String result = HttpRequestUtils.doPost("https://api.zju.edu.cn/api/dingriliusradd/service",map,"utf-8");
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
