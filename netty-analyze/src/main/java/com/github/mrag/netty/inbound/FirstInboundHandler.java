package com.github.mrag.netty.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class FirstInboundHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("first inbound");
        ByteBuf buf = ctx.alloc().heapBuffer();
        buf.writeBytes("你也好".getBytes());
        ctx.channel().writeAndFlush(msg);
//        super.channelRead(ctx, msg);
    }
}
