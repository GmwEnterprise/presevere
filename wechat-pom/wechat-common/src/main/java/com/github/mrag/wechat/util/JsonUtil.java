package com.github.mrag.wechat.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public final class JsonUtil {

    public static String toJSON(Object o) {
        return JSONObject.toJSONString(o);
    }

    public static <T> T toObject(Class<T> type, String json) {
        return JSON.parseObject(json, type);
    }
}
