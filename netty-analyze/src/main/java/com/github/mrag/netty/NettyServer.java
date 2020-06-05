package com.github.mrag.netty;

import com.github.mrag.netty.inbound.FirstInboundHandler;
import com.github.mrag.netty.inbound.InboundExpHandler;
import com.github.mrag.netty.outbound.FirstOutboundHandler;
import com.github.mrag.netty.outbound.SecondOutboundHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public void start() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        ServerBootstrap bs = new ServerBootstrap()
                .localAddress(4200)
                .channel(NioServerSocketChannel.class)
                .group(eventLoopGroup)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new SecondOutboundHandler())
                                .addLast(new FirstOutboundHandler())
                                .addLast(new FirstInboundHandler())
                                .addLast(new SecondOutboundHandler())
                                .addLast(new InboundExpHandler());
                    }
                });
        bs.bind().addListener(f -> {
            if (f.isSuccess()) {
                System.out.println("本地端口绑定成功!");
            } else {
                System.err.println("本地端口绑定失败...");
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyServer().start();
    }
}
