package com.github.mrag.rpc.serialization;

public final class SerializationFactory {

    public static final Serialization<Byte> JSON_BYTE_SERIALIZATION = new Serialization<>() {
        @Override
        public Byte serialize(Object in) throws SerializeException {
            return null; // TODO
        }

        @Override
        public Object deserialize(Byte out) throws SerializeException {
            return null; // TODO
        }
    };

    public static Serialization<Byte> defaultJacksonSerialization() {
        return JSON_BYTE_SERIALIZATION;
    }
}
