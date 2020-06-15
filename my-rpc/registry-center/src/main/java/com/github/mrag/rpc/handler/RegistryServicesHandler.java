package com.github.mrag.rpc.handler;

import com.github.mrag.rpc.packet.PacketToCenterRegistryServices;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class RegistryServicesHandler extends SimpleChannelInboundHandler<PacketToCenterRegistryServices> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketToCenterRegistryServices msg) throws Exception {
        // TODO 服务注册
    }
}
