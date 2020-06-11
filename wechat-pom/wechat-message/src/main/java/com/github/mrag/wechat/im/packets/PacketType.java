package com.github.mrag.wechat.im.packets;

public enum PacketType {
    // 心跳
    HEARTBEAT(0, Void.TYPE);

    private final byte type;
    private final Class<?> bodyType;

    PacketType(int type, Class<?> bodyType) {
        this.type = (byte) type;
        this.bodyType = bodyType;
    }

    public static PacketType type(byte type) {
        for (PacketType packetType : values()) {
            if (packetType.type == type) {
                return packetType;
            }
        }
        throw new IllegalArgumentException("没有该packetType");
    }

    public byte getType() {
        return type;
    }

    public Class<?> getBodyType() {
        return bodyType;
    }
}
