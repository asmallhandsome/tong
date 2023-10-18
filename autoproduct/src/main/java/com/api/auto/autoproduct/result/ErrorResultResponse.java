package com.api.auto.autoproduct.result;

import com.alibaba.fastjson2.JSONObject;

public class ErrorResultResponse {

    public static final JSONObject TOKEN_EXCEPTION_RESPONSE = new JSONObject();
    public static final JSONObject PARAMS_EXCEPTION_RESPONSE = new JSONObject();
    public static final JSONObject SECRET_KEY_EXCEPTION_RESPONSE = new JSONObject();
    public static final JSONObject INTERNAL_SERVER_EXCEPTION_RESPONSE = new JSONObject();

    static {
        TOKEN_EXCEPTION_RESPONSE.put("msg", "获取Token出错，值为空！！");
        TOKEN_EXCEPTION_RESPONSE.put("code", 204);
        PARAMS_EXCEPTION_RESPONSE.put("msg", "有必填参数出错，值为空！！");
        PARAMS_EXCEPTION_RESPONSE.put("code", 204);
        SECRET_KEY_EXCEPTION_RESPONSE.put("msg", "应用初始化参数secretKey异常，值为空！！");
        SECRET_KEY_EXCEPTION_RESPONSE.put("code", 204);
        INTERNAL_SERVER_EXCEPTION_RESPONSE.put("msg", "服务器内部错误，无法完成请求！！");
        INTERNAL_SERVER_EXCEPTION_RESPONSE.put("code", 500);
    }



}
