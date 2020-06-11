package com.github.mrag.wechat.im.handlers;

import com.github.mrag.wechat.common.MessageTooShortException;
import com.github.mrag.wechat.im.packets.Packet;
import com.github.mrag.wechat.im.packets.codec.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

public class PacketCodecHandler extends ByteToMessageCodec<Packet<?>> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet<?> msg, ByteBuf out) throws Exception {
        PacketCodec.encode(msg, out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        try {
            out.add(PacketCodec.decode(in));
        } catch (MessageTooShortException ignored) {
            // 包长度不够，忽略错误
        }
    }
}
