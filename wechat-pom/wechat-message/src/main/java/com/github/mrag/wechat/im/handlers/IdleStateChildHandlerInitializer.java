package com.github.mrag.wechat.im.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class IdleStateChildHandlerInitializer extends ChannelInitializer<SocketChannel> {
    private static final Logger log = LoggerFactory.getLogger(IdleStateChildHandlerInitializer.class);

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();

        // 一定时间触发一次IdleStateEvent事件，由HeartbeatHandler接收该事件并发送心跳
        p.addLast(new IdleStateHandler(0, 0, 5, TimeUnit.SECONDS));
        p.addLast(new HeartbeatHandler());

        // 位于pipeline尾端的channel异常处理器
        p.addLast(new NettyExceptionHandler());
    }

    @Sharable
    public static class HeartbeatHandler extends ChannelInboundHandlerAdapter {
        private static ByteBuf HEARTBEAT_SEQUENCE;

        static {
            // 初始化心跳包数据

        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                // 发送心跳包，如果失败则断开连接
                ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
                        .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            } else {
                // 将其他类型事件传递下去
                super.userEventTriggered(ctx, evt);
            }
        }
    }
}
