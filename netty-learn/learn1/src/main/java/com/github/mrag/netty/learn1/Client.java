package com.github.mrag.netty.learn1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public final class Client {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap()
                .group(eventExecutors)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            }
                        });
                    }
                });

        bootstrap.connect("localhost", 4200).addListener((ChannelFutureListener) future -> {
            Channel channel = future.channel();
            channel.eventLoop().scheduleWithFixedDelay(() -> {
                System.out.print("请输入发送的字节：");
                byte b = scanner.nextByte();
                ByteBuf msg = channel.alloc().heapBuffer(1);
                msg.writeByte(b);
                channel.writeAndFlush(msg).addListener(f -> {
                    if (f.isSuccess()) {
                        System.out.println("发送成功！");
                    }
                });
            }, 0L, 1L, TimeUnit.SECONDS);
        });
    }
}
