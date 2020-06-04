package com.github.mrag.netty.inbound;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
public class InboundExpHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close().addListener(f -> {
            if (f.isSuccess()) {
                System.err.println(">>> 错误信息：" + cause.getMessage());
            } else {
                System.err.println(">>> 关闭连接错误！错误信息：" + cause.getMessage());
            }
        });
    }
}
