package com.github.mrag.wechat.im;

import com.github.mrag.wechat.im.packets.Packet;
import com.github.mrag.wechat.util.JsonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public final class PacketHelper {

    // FIXME 有问题

    public static <P extends Packet> P decode(Class<P> type, ByteBuf buf) {
        int len = buf.readableBytes();
        byte[] bytes = new byte[len];
        buf.readBytes(bytes);
        return JsonUtil.toObject(type, new String(bytes));
    }

    public static <P extends Packet> ByteBuf encode(P packet, ByteBufAllocator alloc) {
        byte[] packetBytes = JsonUtil.toJSON(packet).getBytes();
        ByteBuf buf = alloc.buffer(packetBytes.length);
        buf.writeBytes(packetBytes);
        return buf;
    }
}
