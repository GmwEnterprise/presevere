package org.example.handlers.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.LoginUtils;
import org.example.packet.LoginRequestPacket;
import org.example.packet.LoginResponsePacket;
import org.example.packet.PacketCodeC;

import java.util.Date;
import java.util.UUID;

@ChannelHandler.Sharable
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
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
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket lrp) throws Exception {
        if (lrp.getSuccess()) {
            System.out.println(new Date() + ": 客户端登陆成功");
            LoginUtils.markAsLogin(ctx.channel());
        } else {
            System.out.println(new Date() + ": 客户端登陆失败, 原因: " + lrp.getReason());
        }
    }
}
