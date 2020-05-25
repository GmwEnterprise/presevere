package org.example.handlers.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.Session;
import org.example.SessionUtils;
import org.example.packet.MessageRequestPacket;
import org.example.packet.MessageResponsePacket;
import org.example.packet.PacketCodeC;

import java.util.Date;

import static org.example.Attributes.SESSION;

@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket mrp) throws Exception {
        Session fromSession = ctx.channel().attr(SESSION).get();
        Channel toChannel = SessionUtils.getChannel(mrp.getTo());

        MessageResponsePacket response = new MessageResponsePacket();
        response.setMessage("来自「" + fromSession.getUsername() + "」的消息回复: " + mrp.getMessage());
        response.setFromUserId(fromSession.getUserId());
        response.setFromUsername(fromSession.getUsername());
        response.setVersion(mrp.getVersion());

        if (toChannel != null && toChannel.hasAttr(SESSION)) {
            System.out.printf("%s: 收到客户端消息: [%s], 发送给: %s%n", new Date(), mrp.getMessage(),
                    toChannel.attr(SESSION).get().getUsername());
            ByteBuf msg = PacketCodeC.INSTANCE.encode(response, toChannel.alloc());
            toChannel.writeAndFlush(msg);
        } else {
            System.err.println("[" + mrp.getTo() + "]不在线, 发送失败!");
        }
    }
}
