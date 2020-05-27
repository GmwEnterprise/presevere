package com.github.mrag.wechat.im.handlers;

import com.github.mrag.wechat.im.PacketHelper;
import com.github.mrag.wechat.im.packets.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoderInboundHandler extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Packet packet = PacketHelper.decode(Packet.class, in);

        // FIXME 有问题
    }
}
