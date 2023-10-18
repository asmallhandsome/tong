package com.api.auto.autoproduct.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TemplateUtil {
    public static String createApiTemplate(String className, String methodName, ArrayList<List> params, String url, String zhuShi){

        // 形参的构建对象
        StringBuilder builderParams =  new StringBuilder();
        // 具体必填参数的构建对象
        StringBuilder filledParams = new StringBuilder();
        // 全部具体参数的构建对象
        StringBuilder paramsString = new StringBuilder();
        // 循环遍历参数
        for (int k = 0; k < params.size(); k++) {
            String fieldName = params.get(k).get(1).toString();
            String fieldType = params.get(k).get(2).toString();
            // 1为必填字段0为非必填字段
            Integer fieldRequired = (Integer)params.get(k).get(3);
            fieldType = ApiUtil.getFirstCharUpper(fieldType);
            // 必填字段为空处理
            if(fieldRequired==1){
                if (fieldType.equals("String")) {
                    filledParams.append("            if (!StringUtils.hasText(").append(fieldName)
                            .append(")) {\n")
                            .append("                return ErrorResultResponse.PARAMS_EXCEPTION_RESPONSE;\n")
                            .append("            }\n");
                } else {
                    filledParams.append("            if (!StringUtils.hasText(").append(fieldName)
                            .append(".toString())) {\n")
                            .append("                return ErrorResultResponse.PARAMS_EXCEPTION_RESPONSE;\n")
                            .append("            }\n");
                }
            }
            // 所有不为空字段处理
            if (fieldType.equals("String")) {
                paramsString.append("            if (StringUtils.hasText(").append(fieldName).append(")) {\n")
                        .append("                parameter.put(\"").append(fieldName).append("\",").append(fieldName).append(");\n")
                        .append("            }\n");
            } else {
                paramsString.append("            if (StringUtils.hasText(").append(fieldName).append(".toString())) {\n")
                        .append("                parameter.put(\"").append(fieldName).append("\",").append(fieldName).append(");\n")
                        .append("            }\n");
            }

            // 方法单个形参
            String param = fieldType + " " + fieldName+", ";
            // 方法组合的形参
            builderParams.append(param);
        }

        // 方法形参字符串
        String initParams =builderParams.toString();
        initParams = initParams.substring(0,initParams.lastIndexOf(", "));
        // 方法中具体的参数组成
        String filledString = filledParams.toString();
        String allParamsString = paramsString.toString();

        StringBuilder builder =  new StringBuilder();
        builder.append("package com.api.auto.autoproduct.controller;")
                .append("\n")
                .append("import com.alibaba.fastjson2.JSONObject;\n")
                .append("import com.api.auto.autoproduct.util.HttpRequestUtils;\n")
                .append("import com.api.auto.autoproduct.result.ErrorResultResponse;\n")
                .append("import com.api.auto.autoproduct.util.ApiUtil;\n")
                .append("import com.api.auto.autoproduct.util.AppUtil;\n")
                .append("import org.springframework.util.StringUtils;\n")
                .append("import java.util.HashMap;\n")
                .append("import java.util.Map;\n")
                .append("import java.util.Date;\n")
                .append("\n")
                .append("/**\n")
                .append("* @createDate:").append(new Date().toString()).append("\n")
                .append("* @description:用于").append(zhuShi).append("\n")
                .append("* */\n")
                .append("public class ")
                .append(className).append("{\n")
                .append("\n")
                .append("    public static JSONObject ")
                .append(methodName)
                .append("(String appid, ")
                .append(initParams)
                .append("){\n")

                .append("        try {\n")
                .append("\n")
                .append("            // 必填参数为空处理\n")
                .append("            if (!StringUtils.hasText(appid)) {\n")
                .append("                return ErrorResultResponse.PARAMS_EXCEPTION_RESPONSE;\n")
                .append("            }\n")
                .append(filledString)
                .append("\n")
                .append("            JSONObject reqJSON = new JSONObject();\n")
                .append("            JSONObject parameter = new JSONObject();\n")
                .append("\n")
                .append("            // 所有参数不为空处理\n")
                .append("            if (StringUtils.hasText(appid)) {\n")
                .append("                parameter.put(\"appid\", appid);\n")
                .append("            }\n")
                .append(allParamsString)
                .append("\n")
                .append("            // 获取token并判断是否有值\n")
                .append("            String token = AppUtil.getToken(appid);\n")
                .append("            if (!StringUtils.hasText(token)) {\n")
                .append("                return ErrorResultResponse.TOKEN_EXCEPTION_RESPONSE;\n")
                .append("            }\n")
                .append("\n")
                .append("            // 封装参数\n")
                .append("            reqJSON.put(\"token\", token);\n")
                .append("            reqJSON.put(\"parameter\", parameter);\n")
                .append("\n")
                .append("            // 获取秘钥\n")
                .append("            String secretKey = AppUtil.getSecret(appid);\n")
                .append("            if(!StringUtils.hasText(secretKey)) {\n")
                .append("                return ErrorResultResponse.SECRET_KEY_EXCEPTION_RESPONSE;\n")
                .append("            }\n")
                .append("\n")
                .append("            // 获取加密后的code和签名signature\n")
                .append("            String code = ApiUtil.getCode(reqJSON.toString());\n")
                .append("            String signature = ApiUtil.getSignature(code, secretKey);\n")
                .append("            Map<String,String> map = new HashMap<>();\n")
                .append("            map.put(\"signature\", signature);\n")
                .append("            map.put(\"code\", code);\n")
                .append("\n")
                .append("            // http获取最后的返回结果\n")
                .append("            String result = HttpRequestUtils.doPost(\"")
                .append(url)
                .append("\",map,\"utf-8\");\n")
                .append("            JSONObject jsonResponse = JSONObject.parseObject(result);\n")
                .append("\n")
                .append("            // 判断是否成功获取信息\n")
                .append("            if (\"success\".equals(jsonResponse.getString(\"result\"))) {\n")
                .append("                // 操作成功，单条数据放在data.entity,多条数据，放在data.list\n")
                .append("                return jsonResponse;\n")
                .append("            } else {\n")
                .append("                return jsonResponse;\n")
                .append("            }\n")
                .append("        } catch (Exception e) {\n")
                .append("            e.printStackTrace();\n")
                .append("            return ErrorResultResponse.INTERNAL_SERVER_EXCEPTION_RESPONSE;\n")
                .append("          }\n")
                .append("    }\n")
                .append("}\n");
        String method = builder.toString();

        return method;
    }
}
