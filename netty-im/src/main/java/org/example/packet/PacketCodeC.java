package org.example.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.example.JSONSerializer;
import org.example.Serializer;

import java.util.HashMap;
import java.util.Map;

import static org.example.Command.*;

public class PacketCodeC {
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private PacketCodeC() {
    }

    public static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public ByteBuf encode(Packet packet) {
        ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
        return encode(packet, allocator);
    }

    public ByteBuf encode(Packet packet, ByteBufAllocator allocator) {
        ByteBuf buf = allocator.buffer();
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        buf.writeInt(MAGIC_NUMBER);
        buf.writeByte(packet.getVersion());
        buf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        buf.writeByte(packet.getCommand());
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        return buf;
    }

    public Packet decode(ByteBuf buf) {
        buf.skipBytes(4); // 跳过MAGIC_NUMBER
        buf.skipBytes(1); // 跳过VERSION
        byte serializeAlgorithm = buf.readByte(); // 序列化算法标识
        byte command = buf.readByte(); // 指令
        int length = buf.readInt(); // 数据包长度
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);
        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
