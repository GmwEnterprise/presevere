package com.github.mrag.wechat.im.handlers;

import com.github.mrag.wechat.common.IllegalParameterException;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class NettyExceptionHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(NettyExceptionHandler.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IllegalParameterException) {
            // 错误的参数，可能是来自远程的非法访问，直接关闭
            ctx.channel().closeFuture();
        } else {
            // 触发异常则关闭连接
            log.error("连接[" + ctx.channel().remoteAddress() + "]错误, 将关闭连接并打印异常栈", cause);
            ctx.channel().closeFuture();
        }
    }
}
