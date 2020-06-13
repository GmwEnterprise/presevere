package com.github.mrag.rpc.handler;

import com.github.mrag.rpc.packet.Packet;
import com.github.mrag.rpc.packet.PacketType;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

@ChannelHandler.Sharable
public class PacketRouter extends ChannelInboundHandlerAdapter {
    private final Map<PacketType, SimpleChannelInboundHandler<? extends Packet>> router = Map.of(
            // 心跳包不会收到，因为心跳包是服务端发送出去而不是接收进来
            PacketType.TO_CENTER_CONSUMER_CALL_SERVICE, new ConsumerCallServiceHandler()
    );

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Packet) {
            SimpleChannelInboundHandler<? extends Packet> handler = router.get(((Packet) msg).type());
            if (handler != null) {
                handler.channelRead(ctx, msg);
            }
        } else {
            super.channelRead(ctx, msg);
        }
    }
}
