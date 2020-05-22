package org.example.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.LoginRequestPacket;
import org.example.PacketCodeC;

import java.util.Date;
import java.util.UUID;

@ChannelHandler.Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端开始登陆");

        // 登陆对象创建
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(UUID.randomUUID().toString());
        packet.setUsername("flash");
        packet.setPassword("pwd");

        // 编码
        ByteBuf buf = PacketCodeC.INSTANCE.encode(packet, ctx.alloc());

        // 写数据
        ctx.channel().writeAndFlush(buf);
    }
}
