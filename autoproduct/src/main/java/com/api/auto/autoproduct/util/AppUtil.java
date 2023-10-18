package com.api.auto.autoproduct.util;

import com.api.auto.autoproduct.result.AppInfo;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AppUtil {

    static Map<String, AppInfo> map = new ConcurrentHashMap<>();


    public static void initParams(String appId, String appKey, String secretKey) {
        AppInfo appInfo = map.get(appId);
        if (appInfo == null) {
            appInfo = new AppInfo();
            map.put(appId, appInfo);
        }
        appInfo.init(appId, appKey, secretKey);
    }

    public static String getToken(String appId) {
        AppInfo appInfo = map.get(appId);
        String token = null;
        if (appInfo != null) {
            token = appInfo.getToken();
        }
        return token;
    }

    public void removeApp(String appId) {

    }

    public static String getSecret(String appId) {
        AppInfo appInfo = map.get(appId);
        String secretKey = null;
        if (appInfo != null) {
            secretKey = appInfo.getSecretKey();
        }
        return secretKey;
    }

}
