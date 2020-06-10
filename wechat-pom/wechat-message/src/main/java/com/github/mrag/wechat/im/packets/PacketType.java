package com.github.mrag.wechat.im.packets;

public enum PacketType {
    // 心跳
    HEARTBEAT(0);

    private final byte type;

    PacketType(int type) {
        this.type = (byte) type;
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
}
