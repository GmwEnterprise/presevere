package com.github.mrag.wechat.im.packets.codec;

import com.github.mrag.wechat.common.IllegalParameterException;
import com.github.mrag.wechat.common.MessageTooShortException;
import com.github.mrag.wechat.im.packets.EncodingMode;
import com.github.mrag.wechat.im.packets.Packet;
import com.github.mrag.wechat.im.packets.PacketType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class PacketCodec {
    private static final Map<EncodingMode, Codec> encodingModeMap = new ConcurrentHashMap<>();

    /**
     * 所有Codec实现类，都会在被spring加载时，在构造函数中调用该方法从而注入自己
     */
    public static void setEncodingModeMap(EncodingMode mode, Codec codec) {
        encodingModeMap.putIfAbsent(mode, codec);
    }

    private static final ByteBuf HEARTBEAT_PACKET;

    static {
        HEARTBEAT_PACKET = Unpooled.unreleasableBuffer(Unpooled.buffer(Packet.PacketHeader.HEADER_LEN)
                .writeLong(0L)
                .writeByte(PacketType.HEARTBEAT.getType())
                .writeByte(EncodingMode.NON_ENCODING.getMode())
                .writeInt(0));
    }

    public static <T> ByteBuf encode(Packet<T> packet, ByteBuf out) {
        Packet.PacketHeader header = packet.getHeader();
        if (header.getPacketType().equals(PacketType.HEARTBEAT)) {
            // 心跳包直接使用常量值
            return HEARTBEAT_PACKET.duplicate();
        } else {
            Codec codec = encodingModeMap.get(header.getEncodingMode());
            byte[] body;
            try {
                body = codec.encode(packet.getBody());
            } catch (Exception e) {
                throw new IllegalParameterException("编码失败. ", e);
            }
            return out.writeLong(System.currentTimeMillis())
                    .writeByte(header.getPacketType().getType())
                    .writeByte(header.getEncodingMode().getMode())
                    .writeInt(body.length)
                    .writeBytes(body);
        }
    }

    public static <T> ByteBuf encode(Packet<T> packet, ByteBufAllocator alloc) {
        return encode(packet, alloc.buffer());
    }

    public static Packet<Object> decode(ByteBuf in) {
        if (in.readableBytes() < Packet.PacketHeader.HEADER_LEN) {
            // 如果没有添加长度域划分的handler，那么该异常将起到作用
            throw new MessageTooShortException();
        }
        long sendTimeMills = in.readLong();
        byte type = in.readByte();
        if (type == PacketType.HEARTBEAT.getType()) {
            throw new IllegalParameterException("异常数据, 心跳包只会被服务端发送而不应该接收");
        } else {
            byte method = in.readByte();
            int bodyLen = in.readInt();

            if (in.readableBytes() < bodyLen) {
                // 如果没有添加长度域划分的handler，那么该异常将起到作用
                // 读指针复位
                in.readerIndex(0);
                throw new MessageTooShortException();
            }

            // 构建包头
            Packet.PacketHeader header = new Packet.PacketHeader();
            header.setSendTimeMill(sendTimeMills);
            header.setPacketType(PacketType.type(type));
            header.setEncodingMode(EncodingMode.mode(method));
            header.setBodyLength(bodyLen);

            // 构建包体
            byte[] bodyDst = new byte[bodyLen];
            in.readBytes(bodyDst);

            Class<?> bodyType = header.getPacketType().getBodyType();
            Codec codec = encodingModeMap.get(header.getEncodingMode());

            // 返回构建好的数据包
            try {
                Object body = codec.decode(bodyDst, bodyType);
                return new Packet<>(header, body);
            } catch (Exception e) {

                // 该异常将由Pipeline中的异常Handler统一处理
                throw new IllegalParameterException("解码失败. ", e);
            }
        }
    }
}
