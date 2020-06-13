package com.github.mrag.rpc;

import com.github.mrag.rpc.handler.PacketRouter;
import com.github.mrag.rpc.packet.Packet;
import com.github.mrag.rpc.packet.PacketToClientHeartbeat;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

public class RemoteChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final ByteToMessageCodec<Packet> codec;
    private static final PacketToClientHeartbeat HEARTBEAT_PACKET = new PacketToClientHeartbeat();

    public RemoteChannelInitializer(ByteToMessageCodec<Packet> codec) {
        this.codec = codec;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();

        // 序列化
        p.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));
        p.addLast(codec);

        // 处理心跳
        p.addLast(new IdleStateHandler(0, 0, 10));
        p.addLast(new ChannelInboundHandlerAdapter() {
            @Override
            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                if (evt instanceof IdleStateEvent) {
                    ctx.writeAndFlush(HEARTBEAT_PACKET).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                } else {
                    super.userEventTriggered(ctx, evt);
                }
            }
        });

        // 处理packet
        p.addLast(new PacketRouter());
    }
}
