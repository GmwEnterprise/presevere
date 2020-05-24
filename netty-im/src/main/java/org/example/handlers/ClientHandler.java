package org.example.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.LoginUtils;
import org.example.packet.*;

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

        System.out.printf("%s: 登录信息：%s%n", new Date(), packet);

        // 编码
        ByteBuf buf = PacketCodeC.INSTANCE.encode(packet, ctx.alloc());

        // 写数据
        ctx.channel().writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(buf);
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket lrp = (LoginResponsePacket) packet;
            if (lrp.getSuccess()) {
                System.out.println(new Date() + ": 客户端登陆成功");
                LoginUtils.markAsLogin(ctx.channel());
            } else {
                System.out.println(new Date() + ": 客户端登陆失败, 原因: " + lrp.getReason());
            }
        } else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket mrp = (MessageResponsePacket) packet;
            System.out.printf("%s: 收到服务端回复消息: [%s]%n", new Date(), mrp.getMessage());
        }
    }
}
