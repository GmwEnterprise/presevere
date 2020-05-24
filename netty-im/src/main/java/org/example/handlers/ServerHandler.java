package org.example.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.packet.*;

import java.util.Date;

@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf requestByteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);
        if (packet instanceof LoginRequestPacket) {
            System.out.printf("%s: 收到登陆请求...%n", new Date());
            LoginRequestPacket lrp = (LoginRequestPacket) packet;
            LoginResponsePacket lrsp = new LoginResponsePacket();
            lrsp.setVersion(packet.getVersion());
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
        } else if (packet instanceof MessageRequestPacket) {
            MessageRequestPacket mrp = (MessageRequestPacket) packet;
            System.out.printf("%s: 收到客户端消息: [%s], 即将回复...%n", new Date(), mrp.getMessage());

            MessageResponsePacket response = new MessageResponsePacket();
            response.setMessage("服务端回复[" + mrp.getMessage() + "]");
            ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(response, ctx.alloc());
            ctx.channel().writeAndFlush(byteBuf);
        }
    }

    private boolean valid(LoginRequestPacket lrp) {
        return true;
    }
}
