package com.github.mrag.rpc.handler;

import com.github.mrag.rpc.packet.PacketToCenterConsumerCallService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class ConsumerCallServiceHandler extends SimpleChannelInboundHandler<PacketToCenterConsumerCallService> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketToCenterConsumerCallService packet) throws Exception {
        // TODO 消费者请求调用服务
    }
}
