package com.github.mrag.netty.client;

import com.github.mrag.netty.inbound.FirstInboundHandler;
import com.github.mrag.netty.inbound.SecondInboundHandler;
import com.github.mrag.netty.outbound.FirstOutboundHandler;
import com.github.mrag.netty.outbound.SecondOutboundHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bs = new Bootstrap()
                .group(new NioEventLoopGroup())
                .remoteAddress("localhost", 4200)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                ByteBuf msg = ctx.alloc().heapBuffer();
                                msg.writeBytes("你好".getBytes());
                                ctx.writeAndFlush(msg);
                            }

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println("client read");
                                super.channelRead(ctx, msg);
                            }
                        });
                    }
                });
        bs.connect().sync();


    }
}
