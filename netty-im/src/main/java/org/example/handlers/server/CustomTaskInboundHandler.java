package org.example.handlers.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * 服务端应用
 * 自定义处理器，实现一些功能
 */
public class CustomTaskInboundHandler extends ChannelInboundHandlerAdapter {
    private long counter = 0L;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        counter += ((ByteBuf) msg).readableBytes();
        System.out.println(new Date() + ": 入站流量[" + counter + " Bytes], 客户端[" + ctx.channel().remoteAddress() + "]");
        super.channelRead(ctx, msg);
    }
}
