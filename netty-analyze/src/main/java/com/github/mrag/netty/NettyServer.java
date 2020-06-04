package com.github.mrag.netty;

import com.github.mrag.netty.inbound.FirstInboundHandler;
import com.github.mrag.netty.inbound.InboundExpHandler;
import com.github.mrag.netty.outbound.FirstOutboundHandler;
import com.github.mrag.netty.outbound.SecondOutboundHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class NettyServer {

    public void start() throws InterruptedException {
        ServerBootstrap bs = new ServerBootstrap()
                .localAddress(4200)
                .channel(NioServerSocketChannel.class)
                .group(new NioEventLoopGroup())
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new SecondOutboundHandler())
                                .addLast(new FirstOutboundHandler())
                                .addLast(new FirstInboundHandler())
                                .addLast(new SecondOutboundHandler())
                                .addLast(new InboundExpHandler());

                        ScheduledFuture<?> f = ch.eventLoop()
                                .scheduleAtFixedRate(
                                        () -> System.out.println("task..."),
                                        3L,
                                        3L,
                                        TimeUnit.SECONDS);
                    }
                });
        ChannelFuture f = bs.bind().sync();
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyServer().start();
    }
}
