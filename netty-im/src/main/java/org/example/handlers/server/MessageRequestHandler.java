package org.example.handlers.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.packet.MessageRequestPacket;
import org.example.packet.MessageResponsePacket;
import org.example.packet.PacketCodeC;

import java.util.Date;

@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket mrp) throws Exception {
        System.out.printf("%s: 收到客户端消息: [%s], 即将回复...%n", new Date(), mrp.getMessage());
        MessageResponsePacket response = new MessageResponsePacket();
        response.setMessage("服务端回复[" + mrp.getMessage() + "]");
        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(response, ctx.alloc());
        ctx.channel().writeAndFlush(byteBuf);
    }
}
