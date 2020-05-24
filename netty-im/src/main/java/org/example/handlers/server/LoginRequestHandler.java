package org.example.handlers.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.packet.LoginRequestPacket;
import org.example.packet.LoginResponsePacket;
import org.example.packet.PacketCodeC;

import java.util.Date;

@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket lrp) throws Exception {
        System.out.printf("%s: 收到登陆请求...%n", new Date());
        LoginResponsePacket lrsp = new LoginResponsePacket();
        lrsp.setVersion(lrp.getVersion());
        if (valid(lrp)) {
            // 校验成功
            lrsp.setSuccess(true);
            System.out.printf("%s: 客户端登陆成功! 登陆信息：%s%n", new Date(), lrp);
        } else {
            // 校验失败
            lrsp.setSuccess(false);
            lrsp.setReason("账号密码校验失败");
        }
        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(lrsp, ctx.alloc());
        ctx.channel().writeAndFlush(byteBuf);
    }

    private boolean valid(LoginRequestPacket lrp) {
        return true;
    }
}
