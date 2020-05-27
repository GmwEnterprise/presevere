package com.github.mrag.wechat.im.packets;

import com.github.mrag.wechat.im.EnumCommand;
import com.github.mrag.wechat.im.serialization.EnumSerializationMethod;
import com.github.mrag.wechat.im.serialization.HessianSerialization;
import com.github.mrag.wechat.im.serialization.JsonSerialization;
import com.github.mrag.wechat.im.serialization.Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

public final class PacketCodec {

    private static final Map<EnumCommand, Class<? extends Packet>> typeMap = new HashMap<>();
    private static final Map<EnumSerializationMethod, Serialization> serializationMap = new HashMap<>();

    static {
        typeMap.put(EnumCommand.SINGLE_MSG_TO_ONE_USER, SingleMsgToOneUserPacket.class);
        typeMap.put(EnumCommand.SINGLE_MSG_TO_MANY_USER, SingleMsgToManyUserPacket.class);

        serializationMap.put(EnumSerializationMethod.JSON, new JsonSerialization());
        serializationMap.put(EnumSerializationMethod.HESSIAN, new HessianSerialization());
    }

    public ByteBuf encode(Packet packet, ByteBufAllocator alloc) {
        ByteBuf buf = alloc.buffer();
        Class<? extends Packet> packetType = typeMap.get(packet.command());
        
        // TODO 未完成
        return buf;
    }
}
