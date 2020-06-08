package com.github.mrag.wechat.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class JacksonUtil {

    @Resource(name = "objectMapper")
    private ObjectMapper objectMapper;

    public String serialize(Object o) throws Exception {
        return objectMapper.writeValueAsString(o);
    }

    public <T> T deserialize(Class<T> type, String json) throws Exception {
        return objectMapper.readValue(json, type);
    }
}
