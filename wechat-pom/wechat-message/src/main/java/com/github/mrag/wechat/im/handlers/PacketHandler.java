package com.github.mrag.wechat.im.handlers;

import com.github.mrag.wechat.im.packets.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class PacketHandler extends MessageToMessageDecoder<Packet<?>> {
    @Override
    protected void decode(ChannelHandlerContext ctx, Packet<?> packet, List<Object> out) throws Exception {
        out.add(packet.getBody());
    }
}
