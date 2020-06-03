package com.github.mrag.netty.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class FirstInboundHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("first inbound");
        ByteBuf buf = ctx.alloc().heapBuffer();
        buf.writeBytes("你也好".getBytes());
        ctx.channel().writeAndFlush(buf);

        if (ReferenceCountUtil.release(msg)) {
            System.out.println("资源释放成功");
        }
    }
}
