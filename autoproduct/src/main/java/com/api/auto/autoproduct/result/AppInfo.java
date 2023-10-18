package com.api.auto.autoproduct.result;

import com.alibaba.fastjson2.JSONObject;
import com.api.auto.autoproduct.util.HttpRequestUtils;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;


public class AppInfo {
    private long startTime;
    private String token;
    private String appId;
    private String appKey;
    @Getter
    private String secretKey;
    private int tryNum;
    private long lastErrTime;


    public void init(String appId, String appKey, String secretKey) {
        this.appId = appId;
        this.appKey = appKey;
        this.secretKey = secretKey;
        this.startTime = 0L;
        this.tryNum = 3;
        this.lastErrTime = 0L;

    }

    public String getToken() {
        Long expireTime = System.currentTimeMillis() - startTime;
        if (expireTime <= 1800000 && StringUtils.hasText(token))
            return token;
        //判断异常获取token的尝试次数，300000毫秒即5分钟内异常3次则直接返回
        if (tryNum == 0 && System.currentTimeMillis() - lastErrTime < 300000)
            return null;
        synchronized (this) {
            try {
                //1800000毫秒为30分钟
                if (expireTime <= 1800000 && StringUtils.hasText(token))
                    return token;
                Map<String, String> map = new HashMap<>();
                map.put("appid", appId);
                map.put("appkey", appKey);
                String tokenEntity = HttpRequestUtils.doGet("https://api.zju.edu.cn/api/reqtoken", map, "UTF-8");

                JSONObject object = JSONObject.parseObject(tokenEntity);
                JSONObject data = (JSONObject) object.get("data");
                token = data.getString("token");
                startTime = System.currentTimeMillis();
                tryNum = 3;
            } catch (Exception e) {
                lastErrTime = System.currentTimeMillis();
                tryNum--;
            }
        }
        return token;
    }


    public void release() {

    }


}
