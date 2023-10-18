//package com.api.auto.autoproduct.controller;
//
//
//import com.alibaba.fastjson2.JSONObject;
//import com.api.auto.autoproduct.result.Api;
//import com.api.auto.autoproduct.result.AppInfo;
//import com.api.auto.autoproduct.result.ResponseResult;
//import com.api.auto.autoproduct.util.AppUtil;
//import com.api.auto.autoproduct.util.HttpRequestUtils;
//import com.api.auto.autoproduct.util.Utils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//
//@RestController
//@RequestMapping("/api")
//public class APIController {
//
//    private int i = 1;
//    private Map<String, AppInfo> appInfoObjects;
//    Long flagTime=0l;
//    String token;
//    @RequestMapping("/time")
//    public String time() {
//       if(i==1){
//           AppUtil appUtil = new AppUtil();
//           appUtil.initParams("jjzegtnnuojx","ovQXH5Dg4CdSuG3H","64f5348d537ff5223247");
//           i++;
//           appInfoObjects = appUtil.map;
//       }
//
//
//       AppInfo appInfo = appInfoObjects.get("jjzegtnnuojx");
//       Long  isExpire =System.currentTimeMillis()/(1000*60*1)-flagTime;
//        if(isExpire<=0.30){
//            System.out.println("jichengToken!"+System.currentTimeMillis());
//            return token;
//        }else {
//            token = appInfo.getToken();
//            flagTime= System.currentTimeMillis()/(1000*60*1);
//            System.out.println("latestToken!"+System.currentTimeMillis());
//        }
//        return "ssss";
//    }
//
//    @RequestMapping("/auto")
//    public void apiProduct() {//String type
//
//        try {
//
//            //api列表
//            ArrayList<List> arrayList = Utils.getApiList("30");
//            //for进行遍历列表信息
//            for (int i = 0; i < arrayList.size(); i++) {
//                //取userID的接口测试
//                String apiId = (String) arrayList.get(0).get(0);
//                //获取接口文档中的输入参数和输出参数及api信息
//                JSONObject jsonObject = new JSONObject();
//                JSONObject jsonObjectSon = new JSONObject();
//                jsonObject.put("action", "ApiHomeService");
//                jsonObject.put("dest", "MDCDataApiDoc");
//                jsonObject.put("operation", "apidoclist");
//                //各个接口的id参数动态化
//                jsonObjectSon.put("apiid", apiId);
//                jsonObject.put("parameter", jsonObjectSon);
//
//                String data = HttpRequestUtils.okHttpRequest("https://api.zju.edu.cn/DoAction.action",jsonObject.toString());
//                ResponseResult responseResult2 = JSONObject.parseObject(data, ResponseResult.class);
//                Api api = responseResult2.getData().getApi();
//                //输出api信息在控制台
////                System.out.println(api);
//                String methodName = api.getName();
//                String className = Utils.getCharUpper(methodName);
//                String type = api.getType();
//                String url ="https://api.zju.edu.cn/api/"+methodName+"/"+type;
//
//                ArrayList<List> params = responseResult2.getData().getParams();
//                StringBuilder builderParams =  new StringBuilder();
//                StringBuilder jsonSon = new StringBuilder();
//
//                for (int k = 0; k < params.size(); k++) {
//                    String fieldName = params.get(k).get(1).toString();
//                    String fieldType = params.get(k).get(2).toString();
//                    Integer fieldIsRequired = (Integer)params.get(k).get(3);
//                    jsonSon.append("            if((").append(fieldName).append(" == null || ").append(fieldName).append(" ==\"\") && ").append(fieldIsRequired).append("!=0){\n" )
//                            .append("                jsonResponse.put(\"").append(fieldName).append("\",").append(fieldName).append(");\n")
//                            .append("                return jsonResponse;\n")
//                            .append("            }\n")
//                            .append("            if(").append(fieldName).append("!=null && ").append(fieldName).append("!=\"\"){\n")
//                            .append("                jsonSon.put(\"").append(fieldName).append("\",").append(fieldName).append(");\n")
//                            .append("            }\n");
//                    String param = Utils.getCharUpper(fieldType) + " " + fieldName+", ";
//                    builderParams.append(param);
//                }
//                String initParams =builderParams.toString();
//                initParams = initParams.substring(0,initParams.lastIndexOf(", "));
//                String jsonSonString = jsonSon.toString();
//
//                StringBuilder builder =  new StringBuilder();
//                builder.append("import com.alibaba.fastjson2.JSONObject;\n")
//                        .append("import com.api.auto.autoproduct.result.ResponseResult;\n")
//                        .append("import com.api.auto.autoproduct.util.HttpRequestUtils;\n")
//                        .append("import com.api.auto.autoproduct.util.Utils;\n")
//                        .append("import java.util.ArrayList;\n")
//                        .append("import java.util.HashMap;\n")
//                        .append("import java.util.Map;\n")
//                        .append("import java.util.Date;\n")
//                        .append("\n")
//                        .append("public class ")
//                        .append(className).append("{\n")
//                        .append("    //getUerId\n")
//                        .append("    public static JSONObject ")
//                        .append(methodName)
//                        .append("(String appid,")
//                        .append(initParams)
//                        .append("){\n")
//                        .append("        ResponseResult responseResult = null;\n")
//                        .append("        JSONObject jsonResponse = null;\n")
//                        .append("        try {\n            String token = Utils.getToken();\n")
//                        .append("            JSONObject jsonObject = new JSONObject();\n")
//                        .append("            JSONObject jsonSon = new JSONObject();\n")
//                        .append("            jsonObject.put(\"token\",token);\n")
//                        .append("            if(appid != null &&appid != \"\"){\n")
//                        .append("               jsonSon.put(\"appid\",appid);\n")
//                        .append("            }else{\n")
//                        .append("               return null;\n")
//                        .append("            }\n");
//
//                builder.append(jsonSonString);
//
//                builder.append("            jsonObject.put(\"parameter\",jsonSon);\n")
//                        .append("            String json = jsonObject.toString();\n")
//                        .append("            ArrayList<String> secret = Utils.secret(json);\n")
//                        .append("            Map<String,String> map = new HashMap<>();\n")
//                        .append("            map.put(\"signature\",secret.get(1));\n")
//                        .append("            map.put(\"code\",secret.get(0));\n")
//                        .append("            String result = HttpRequestUtils.doPost(\"")
//                        .append(url)
//                        .append("\",map,\"utf-8\");\n")
//                        .append("            responseResult = JSONObject.parseObject(result, ResponseResult.class);\n")
//                        .append("            jsonResponse = JSONObject.parseObject(result);\n")
//                        .append("        } catch (Exception e) {\n")
//                        .append("            e.printStackTrace();\n            }\n")
//                        .append("        if(\"success\".equals(responseResult.getResult())) {\n")
//                        .append("            //操作成功，如果单条数据放在data.entity下,如果多条数据，放在data.list下，格式参考上面的返回结果说明\n")
//                        .append("            return jsonResponse;\n")
//                        .append("        }else{\n")
//                        .append("            return jsonResponse;\n")
//                        .append("        }\n")
//                        .append("    }\n")
//                        .append("}\n");
//                String method = builder.toString();
//
//                File file = new File("src/main/java/com/api/auto/autoproduct/controller/"+className+".java");
//                FileOutputStream fileOutputStream = new FileOutputStream(file);
//                byte[] bytes = method.getBytes();
//                fileOutputStream.write(bytes, 0, bytes.length);
//                fileOutputStream.close();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
////    @RequestMapping("/test")
////    public String apiTest() {
////
////        String string = null;
////        try {
////            //------------------------------httpclient未封装版测试
//////            List<NameValuePair> params=new ArrayList<>();
//////
//////            params.add(new BasicNameValuePair("signature","mZaOcskFRAcVc9+5+cYhamq4LUTRXBTlo3Wdi0fx2rc="));
//////            params.add(new BasicNameValuePair("code","eyJhY3Rpb24iOiJNRENEYXRhU2VydmljZSIsIm9wZXJhdGlvbiI6InF1ZXJ5IiwiZGVzdCI6ImRpbmd1c2VyaWQiLCJ0b2tlbiI6IjY0ZjUzNDhkNTM4MGY1MjIzMjQ3IiwicGFyYW1ldGVyIjp7ImFwcGlkIjoiamp6ZWd0bm51b2p4IiwieGdoIjoiTDIzMTUzNiJ9fQ=="));
//////            RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
//////            HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
//////
//////
//////            HttpPost httpPost = new HttpPost("https://api.zju.edu.cn/api/dinguserid/query");
//////            httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
//////            HttpResponse httpResponse = httpClient.execute(httpPost);
//////
//////            HttpEntity entity = httpResponse.getEntity();
//////            string = EntityUtils.toString(entity, "utf-8");
////            //------------------------------httpclient封装版测试
////            Map<String,String> map = new HashMap<>();
////            map.put("signature","mZaOcskFRAcVc9+5+cYhamq4LUTRXBTlo3Wdi0fx2rc=");
////            map.put("code","aeyJhY3Rpb24iOiJNRENEYXRhU2VydmljZSIsIm9wZXJhdGlvbiI6InF1ZXJ5IiwiZGVzdCI6ImRpbmd1c2VyaWQiLCJ0b2tlbiI6IjY0ZjUzNDhkNTM4MGY1MjIzMjQ3IiwicGFyYW1ldGVyIjp7ImFwcGlkIjoiamp6ZWd0bm51b2p4IiwieGdoIjoiTDIzMTUzNiJ9fQ==");
////            string = HttpRequestUtils.doPost("https://api.zju.edu.cn/api/dinguserid/query", map, "utf-8");
////            System.out.println(string);
////
////
//////------------------------------获取apiid和xgh测试
////
////          /*  OkHttpClient okHttpClient = HttpRequestUtils.getClient();
////            *//*获取浙大钉（30）菜单下的接口列表信息*//*
////            JSONObject object = new JSONObject();
////            object.put("action", "ApiHomeService");
////            object.put("operation", "apilist");
////            object.put("dest", "MDCDataApiType");
////
////            JSONObject objectSon = new JSONObject();
////            objectSon.put("type", "30");
////
////            object.put("parameter", objectSon);
////
////            Request request = new Request.Builder()
////                    .addHeader("content-type", "application/json")
////                    .url("https://api.zju.edu.cn/DoAction.action")
////                    .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString()))
////                    .build();
////
////            String result = okHttpClient.newCall(request).execute().body().string();
////            ResponseResult responseResult = JSONObject.parseObject(result, ResponseResult.class);
////            *//*浙大钉下的接口列表信息*//*
////            ArrayList<List> arrayList = responseResult.getData().getList();
////
////            System.out.println(arrayList);
////
////            String apiId = (String) arrayList.get(1).get(0);
////            String apiId2 = (String) arrayList.get(0).get(0);
////
////            System.out.println(apiId.equals(apiId2));
////
////            *//*获取userid接口的文档信息*//*
////            JSONObject object2 = new JSONObject();
////            JSONObject objectSon2 = new JSONObject();
////            object2.put("action", "ApiHomeService");
////            object2.put("dest", "MDCDataApiDoc");
////            object2.put("operation", "apidoclist");
////
////            objectSon2.put("apiid", apiId);
////            object2.put("parameter", objectSon2);
////
////            Request request2 = new Request.Builder()
////                    .addHeader("content-type", "application/json")
////                    .url("https://api.zju.edu.cn/DoAction.action")
////                    .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object2.toString()))
////                    .build();
////
////            String result2 = okHttpClient.newCall(request2).execute().body().string();
//////            System.out.println(result2);
////            ResponseResult responseResult2 = JSONObject.parseObject(result2, ResponseResult.class);
////            Api api = responseResult2.getData().getApi();
////            ArrayList<List> params = responseResult2.getData().getParams();
////            System.out.println(api.getName());
////
////            String fieldType = params.get(0).get(2).toString();
////            String fieldName = params.get(0).get(1).toString();
////            System.out.println(ToUpper.getCharUpper(fieldType)+" "+fieldName);
////
////
////            OkHttpClient client = client = new OkHttpClient.Builder()
////                    .connectTimeout(5, TimeUnit.SECONDS)
////                    .readTimeout(7, TimeUnit.SECONDS)
////                    .build();
////            Request request3 = new Request.Builder().url("https://api.zju.edu.cn/api/reqtoken?appid=" + "jjzegtnnuojx" + "&appkey="+ "ovQXH5Dg4CdSuG3H")
////                    .build();
////            Response response = client.newCall(request3).execute();
//////            string = response.body().string();
////            String tokenEntity = response.body().string();
////            int indexToken = tokenEntity.lastIndexOf("token");
////            String token = tokenEntity.substring(indexToken + 8, tokenEntity.length() - 3);
////
////            System.out.println(token);*/
////
//////            String secret = secret(string);
//////            System.out.println(secret);
//////            System.out.println(Objects.requireNonNull(string));
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        return string;
////    }
////
//
//}
