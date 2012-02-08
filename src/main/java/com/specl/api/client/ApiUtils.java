package com.specl.api.client;

import java.util.HashMap;
import java.util.Map;

import com.specl.utils.HttpUtil;
import com.specl.utils.JsonUtil;

public class ApiUtils {
    public static <T> T getApiData(Class<T> clazz, String url, String jsonStr, String mothed) throws Exception {
        Map<String, String> paramMap = new HashMap<String, String>();
        url = "http://api.specl.com" + url;
        paramMap.put("jsonStr", jsonStr);
        String returnStr = "";
        if (mothed.equalsIgnoreCase("post")) {
            returnStr = HttpUtil.sendPost(url, paramMap);
        } else {
            returnStr = HttpUtil.sendGet(url, paramMap);
        }
        String result = JsonUtil.jsonToObject(returnStr, "result", String.class);
        if (result.equals("1")) {
            return JsonUtil.jsonToObject(returnStr, "data", clazz);
        } else {
            String errorCode = "";
            String errorMessage = "";
            try {
                errorCode = JsonUtil.jsonToObject(returnStr, "errorCode", String.class);
                errorMessage = JsonUtil.jsonToObject(returnStr, "errorMessage", String.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
