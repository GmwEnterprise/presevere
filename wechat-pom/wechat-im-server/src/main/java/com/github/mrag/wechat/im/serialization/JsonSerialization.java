package com.github.mrag.wechat.im.serialization;

import com.alibaba.fastjson.JSON;
import com.github.mrag.wechat.im.packets.Packet;

public class JsonSerialization implements Serialization {

    @Override
    public byte[] serialize(Packet packet) {
        return JSON.toJSONBytes(packet);
    }

    @Override
    public <T extends Packet> T deserialize(byte[] bytes, Class<T> type) {
        return JSON.parseObject(bytes, type);
    }
}
