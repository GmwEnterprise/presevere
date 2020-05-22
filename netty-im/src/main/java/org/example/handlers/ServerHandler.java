package org.example.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.LoginRequestPacket;
import org.example.Packet;
import org.example.PacketCodeC;

@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf requestByteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket lrp = (LoginRequestPacket) packet;
            if (valid(lrp)) {
                // 校验成功
            } else {
                // 校验失败
            }
        }
    }

    private boolean valid(LoginRequestPacket lrp) {
        return true;
    }
}
