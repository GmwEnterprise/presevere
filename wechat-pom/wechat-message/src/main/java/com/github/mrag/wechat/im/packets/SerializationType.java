package com.github.mrag.wechat.im.packets;

/**
 * 包体序列化方式
 */
public enum SerializationType {
    JSON(1);

    private final byte type;

    SerializationType(int type) {
        this.type = (byte) type;
    }

    public byte getType() {
        return type;
    }

    public static SerializationType type(byte type) {
        for (SerializationType value : values()) {
            if (value.type == type) {
                return value;
            }
        }
        throw new IllegalArgumentException("没有该serializationType");
    }
}
