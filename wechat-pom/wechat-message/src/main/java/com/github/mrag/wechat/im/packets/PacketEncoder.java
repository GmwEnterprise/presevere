package com.github.mrag.wechat.im.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf byteBuf) throws Exception {
        ByteBuf buf = PacketCodec.getInstance().encode(packet, ctx.alloc());
        byteBuf.writeBytes(buf);

        // FIXME 具体使用方式待确认
    }
}
