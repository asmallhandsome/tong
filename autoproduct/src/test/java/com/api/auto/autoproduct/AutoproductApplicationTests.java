package com.api.auto.autoproduct;

import com.alibaba.fastjson2.JSONObject;
import com.api.auto.autoproduct.controller.CreateApiFile;
import com.api.auto.autoproduct.controller.DinguseridApi;
import com.api.auto.autoproduct.util.AppUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AutoproductApplicationTests {

    @Test
    void apiAccessTest() {
        //    String code = "eyJhY3Rpb24iOiJNRENEYXRhU2VydmljZSIsIm9wZXJhdGlvbiI6InF1ZXJ5IiwiZGVzdCI6ImRpbmd1c2VyaWQiLCJ0b2tlbiI6IjY0ZjUzNDhkNTM4MGY1MjIzMjQ3IiwicGFyYW1ldGVyIjp7ImFwcGlkIjoiamp6ZWd0bm51b2p4IiwieGdoIjoiTDIzMTUzNiJ9fQ==";
//    String signature = "mZaOcskFRAcVc9+5+cYhamq4LUTRXBTlo3Wdi0fx2rc=";
//    static final String key ="64f5348d537ff5223247";
        AppUtil.initParams("jjzegtnnuojx","ovQXH5Dg4CdSuG3H","64f5348d537ff5223247");
        JSONObject dinguserid = DinguseridApi.dinguserid("jjzegtnnuojx", "L231536");
//        JSONObject dinguserid = DinguserinfoApi.dinguserinfo("jjzegtnnuojx", "L231536");
//        System.out.println(dinguserid);
        assert dinguserid == null : dinguserid.toString();
    }

    @Test
    void createApiFileTest(){
        CreateApiFile.apiProduct("30");
    }

}
