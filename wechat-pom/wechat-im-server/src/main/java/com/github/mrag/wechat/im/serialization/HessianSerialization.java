package com.github.mrag.wechat.im.serialization;

import com.github.mrag.wechat.im.packets.Packet;

public class HessianSerialization implements Serialization {
    // TODO http://hessian.caucho.com/#Java

    @Override
    public byte[] serialize(Packet packet) {
        return new byte[0];
    }

    @Override
    public <T extends Packet> T deserialize(byte[] bytes, Class<T> type) {
        return null;
    }
}
