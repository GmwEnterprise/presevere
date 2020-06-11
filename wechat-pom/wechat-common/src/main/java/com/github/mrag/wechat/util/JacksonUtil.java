package com.github.mrag.wechat.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JacksonUtil {
    public static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(f));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(f));
        objectMapper.registerModule(module);
    }

    public static String serialize(Object o) throws Exception {
        return objectMapper.writeValueAsString(o);
    }

    public static <T> T deserialize(Class<T> type, String json) throws Exception {
        return objectMapper.readValue(json, type);
    }
}
