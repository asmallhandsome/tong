//package com.api.auto.autoproduct.controller;
//
//import com.alibaba.fastjson2.JSONObject;
//import com.api.auto.autoproduct.util.AppUtil;
//import com.api.auto.autoproduct.util.HttpRequestUtils;
//import com.api.auto.autoproduct.util.ApiUtil;
//import org.springframework.util.StringUtils;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Dinguserid {
//
//        public static JSONObject dinguserid(String appid, String xgh) {
//                JSONObject jsonResponse = null;
//                try {
//
//                        JSONObject jsonObject = new JSONObject();
//                        JSONObject jsonSon = new JSONObject();
//
//                        if (!StringUtils.hasText(appid)) return ApiUtil.paramsException();
//                        if (!StringUtils.hasText(xgh)) return ApiUtil.paramsException();
//
//                        if (StringUtils.hasText(appid)) jsonSon.put("appid", appid);
//                        if (StringUtils.hasText(xgh)) jsonSon.put("xgh", xgh);
//
//                        String token = AppUtil.getToken(appid);
//                        if (!StringUtils.hasText(token)) return ApiUtil.tokenException();
//                        jsonObject.put("token", token);
//
//                        jsonObject.put("parameter", jsonSon);
//                        String json = jsonObject.toString();
//                        String secretKey = AppUtil.getSecret(appid);
//
//                        if(!StringUtils.hasText(secretKey)) return ApiUtil.secretKeyException();
//                        String code = ApiUtil.getCode(json);
//                        String signature = ApiUtil.getSignature(code,secretKey);
//
//                        Map<String, String> map = new HashMap<>();
//                        map.put("code", code);
//                        map.put("signature", signature);
//
//                        String result = HttpRequestUtils.doPost("https://api.zju.edu.cn/api/dinguserid/query", map, "utf-8");
//                        jsonResponse = JSONObject.parseObject(result);
//                        if ("success".equals(jsonResponse.getString("result"))) {
//                                //操作成功，单条数据放在data.entity下,多条数据，放在data.list下
//                                return jsonResponse;
//                        } else {
//                                return jsonResponse;
//                        }
//                } catch (Exception e) {
//                        e.printStackTrace();
//                        return ;
//                }
//        }
//
////        public static void main(String[] args) {
////        System.out.println(dinguserid("jjzegtnnuojx","L231536"));
////        }
//}
