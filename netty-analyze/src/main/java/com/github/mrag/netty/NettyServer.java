package com.github.mrag.netty;

import com.github.mrag.netty.inbound.FirstInboundHandler;
import com.github.mrag.netty.inbound.InboundExpHandler;
import com.github.mrag.netty.inbound.SecondInboundHandler;
import com.github.mrag.netty.outbound.FirstOutboundHandler;
import com.github.mrag.netty.outbound.SecondOutboundHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public void start() throws InterruptedException {
        ServerBootstrap bs = new ServerBootstrap()
                .localAddress(4200)
                .channel(NioServerSocketChannel.class)
                .group(new NioEventLoopGroup())
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new SecondOutboundHandler())
                                .addLast(new FirstInboundHandler())
                                .addLast(new SecondInboundHandler())
                                .addLast(new FirstOutboundHandler())
                                .addLast(new InboundExpHandler());
                    }
                });
        ChannelFuture f = bs.bind().sync();
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyServer().start();
    }
}
