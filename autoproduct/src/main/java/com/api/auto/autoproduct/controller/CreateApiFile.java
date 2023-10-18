package com.api.auto.autoproduct.controller;

import com.alibaba.fastjson2.JSONObject;
import com.api.auto.autoproduct.result.Api;
import com.api.auto.autoproduct.result.ApiInfo;
import com.api.auto.autoproduct.util.ApiUtil;
import com.api.auto.autoproduct.util.HttpRequestUtils;
import com.api.auto.autoproduct.util.TemplateUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateApiFile {

    // api接口要访问的url前缀
    private static final String API_URL = "https://api.zju.edu.cn/api/";

    // apiDoc访问url
    private static final String API_DOC_URL = "https://api.zju.edu.cn/DoAction.action";
    // 文件生成所在的路径
    private static final String PATH = "src/main/java/com/api/auto/autoproduct/controller/";


    public static void apiProduct(String apiListType) {//"20", "30", "60", "90"

        try {
            //api类别的列表
            ArrayList<List> apiTypeList = ApiUtil.getApiList(apiListType);
            if(apiTypeList==null){
                return;
            }
            //for进行遍历列表信息
            for (int i = 0; i < apiTypeList.size(); i++) {
                //取各个api的接id
                String apiId = apiTypeList.get(i).get(0).toString();

                //为请求设置apiID参数
                String apiDocParams = ApiUtil.createApiDocRequestString(apiId);

                //通过每个api的ID,获取具体的接口信息
                String data = HttpRequestUtils.okHttpRequest(API_DOC_URL, apiDocParams);

                JSONObject jsonObject = JSONObject.parseObject(data);
                //判断是否成功获取到apiInfo的信息，成功获取后进行信息处理
                if ("success".equals(jsonObject.getString("result"))) {
                    ApiInfo apiInfo = JSONObject.parseObject(jsonObject.get("data").toString(), ApiInfo.class);
                    Api api = apiInfo.getApi();
                    //用api信息构建接口类名、方法名、URL
                    String zhuShi = api.getSname();
                    String methodName = api.getName();
                    String className = ApiUtil.getFirstCharUpper(methodName);
                    className = className + "Api";
                    String type = api.getType();
                    String url = API_URL + methodName + "/" + type;
                    //用获取的param信息构建接口需要的参数
                    ArrayList<List> params = apiInfo.getParams();
                    //获取模板
                    String method = TemplateUtil.createApiTemplate(className, methodName, params, url, zhuShi);

                    //产生文件对象
                    File file = new File(PATH + className + ".java");
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] bytes = method.getBytes();
                    //生成接口
                    fileOutputStream.write(bytes, 0, bytes.length);
                    fileOutputStream.close();
                }//if结束

            }//for结束

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
