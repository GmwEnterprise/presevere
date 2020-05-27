package com.github.mrag.wechat.im.serialization;

import com.github.mrag.wechat.im.packets.Packet;

public interface Serialization {

    /**
     * 序列化
     */
    byte[] serialize(Packet packet);

    /**
     * 反序列化
     */
    <T extends Packet> T deserialize(byte[] bytes, Class<T> type);
}
