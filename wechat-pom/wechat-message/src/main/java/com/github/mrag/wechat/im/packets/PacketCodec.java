package com.github.mrag.wechat.im.packets;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    private PacketCodec() {
    }

    private static class PacketCodecHolder {
        private static final PacketCodec instance = new PacketCodec();
    }

    public static PacketCodec getInstance() {
        /*
        if (instance == null) {

            // 完成第一次初始化以后，就不会再进入这个同步代码块了
            synchronized (PacketCodec.class) {
                if (instance == null) {
                    instance = new PacketCodec();
                }
            }
        }
        */
        return PacketCodecHolder.instance; // 静态内部类，也属于懒加载，且线程安全
    }

    public ByteBuf encode(Packet packet, ByteBufAllocator alloc) throws JsonProcessingException {
        ByteBuf buf = alloc.buffer();

        EnumCommand cmd = packet.command();
        EnumSerializationMethod method = packet.serializationMethod();

        buf.writeLong(packet.serialVersionUID());
        buf.writeInt(method.getMethod());
        buf.writeInt(cmd.getCommand());

        byte[] body = serializationMap.get(method).serialize(packet);
        buf.writeInt(body.length);
        buf.writeBytes(body);

        return buf;
    }

    public Packet decode(ByteBuf in) throws JsonProcessingException {
        in.readLong(); // 略过serialVersionUID
        int methodVal = in.readInt();
        int cmdVal = in.readInt();
        int bodyLength = in.readInt();
        byte[] body = new byte[bodyLength];
        in.readBytes(body);

        Class<? extends Packet> type = typeMap.get(EnumCommand.of(cmdVal));
        return serializationMap.get(EnumSerializationMethod.of(methodVal)).deserialize(body, type);
    }
}
