package com.github.mrag.wechat.im.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.mrag.wechat.im.packets.Packet;

public interface Serialization {

    /**
     * 序列化
     */
    byte[] serialize(Packet packet) throws JsonProcessingException;

    /**
     * 反序列化
     */
    <T extends Packet> T deserialize(byte[] bytes, Class<T> type) throws JsonProcessingException;
}
