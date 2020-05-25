package org.example.handlers.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@ChannelHandler.Sharable
public class CustomServerHandler extends ChannelInboundHandlerAdapter {
    private final AtomicInteger channelCount = new AtomicInteger();
    private final Thread channelCountTask = new Thread(() -> {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(new Date() + ": 当前客户端连接数：" + channelCount.get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    });

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        channelCountTask.start();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        channelCountTask.interrupt();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        channelCount.incrementAndGet();
        super.channelRead(ctx, msg);
    }
}
