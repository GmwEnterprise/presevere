package com.github.mrag.rpc.serialization;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gmw
 */
public final class SerializationFactory {
    private static final Map<String, Serialization<byte[]>> mapOfSerialBytes = new ConcurrentHashMap<>();

    public static Serialization<byte[]> defaultJackson2BytesSerialization() {
        return mapOfSerialBytes.computeIfAbsent("JACKSON", k -> new JacksonSerializer());
    }
}
