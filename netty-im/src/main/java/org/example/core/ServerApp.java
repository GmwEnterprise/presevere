package org.example.core;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ServerApp {

    public static void main(String[] args) {
        new ServerApp().go();
    }

    public void go() {
        ServerBootstrap bs = new ServerBootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        bs.group(eventLoopGroup, eventLoopGroup)
                .localAddress(5001)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ServerHandler());
                    }
                });
    }
}
