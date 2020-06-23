package com.github.mrag.rpc.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Gmw
 */
final class JacksonSerializer implements Serialization<byte[]> {
    private static final ObjectMapper objectMapper;

    static {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(pattern));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(pattern));

        objectMapper = new ObjectMapper().registerModule(javaTimeModule);
    }

    @Override
    public byte[] serialize(Object in) throws SerializeException {
        try {
            return objectMapper.writeValueAsBytes(in);
        } catch (JsonProcessingException e) {
            throw new SerializeException(e.getMessage());
        }
    }

    @Override
    public <T> T deserialize(byte[] out, Class<T> type) throws SerializeException {
        try {
            return objectMapper.readValue(out, type);
        } catch (IOException e) {
            throw new SerializeException(e.getMessage());
        }
    }
}
