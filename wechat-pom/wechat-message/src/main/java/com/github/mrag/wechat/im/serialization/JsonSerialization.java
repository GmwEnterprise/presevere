package com.github.mrag.wechat.im.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.mrag.wechat.im.packets.Packet;
import com.github.mrag.wechat.util.ApplicationContextUtil;
import com.github.mrag.wechat.util.JacksonUtil;
import io.netty.util.CharsetUtil;

public class JsonSerialization implements Serialization {

    @Override
    public byte[] serialize(Packet packet) throws JsonProcessingException {
        return ApplicationContextUtil.getBean(JacksonUtil.class)
                .serialize(packet)
                .getBytes(CharsetUtil.UTF_8);
    }

    @Override
    public <T extends Packet> T deserialize(byte[] bytes, Class<T> type) throws JsonProcessingException {
        return ApplicationContextUtil.getBean(JacksonUtil.class)
                .deserialize(type, new String(bytes, CharsetUtil.UTF_8));
    }
}
