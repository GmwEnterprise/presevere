package org.example.handlers.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.packet.MessageResponsePacket;

import java.util.Date;

@ChannelHandler.Sharable
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket mrp) throws Exception {
        System.out.printf("%s: 收到服务端回复消息: [%s]%n", new Date(), mrp.getMessage());
    }
}
