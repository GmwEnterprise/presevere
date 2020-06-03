package com.github.mrag.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

public class Client {

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bs = new Bootstrap()
                .group(new NioEventLoopGroup())
                .remoteAddress("localhost", 4200)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        ByteBuf msg = ctx.alloc().heapBuffer();
                                        msg.writeBytes("你好".getBytes());
                                        ctx.channel().writeAndFlush(msg);
                                    }
                                })
                                .addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                        int len = msg.readableBytes();
                                        byte[] target = new byte[len];
                                        msg.readBytes(target);
                                        System.out.println("收到内容：" + new String(target, CharsetUtil.UTF_8));
                                    }
                                });
                    }
                });
        bs.connect().sync();


    }
}
