package org.example.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class CLientApp {

    public static void main(String[] args) {
        new CLientApp().go();
    }

    public void go() {
        Bootstrap bs = new Bootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        bs.group(eventLoopGroup)
                .remoteAddress("localhost", 5001)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
    }
}
