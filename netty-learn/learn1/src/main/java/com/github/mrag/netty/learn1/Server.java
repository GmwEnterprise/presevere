package com.github.mrag.netty.learn1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.net.InetSocketAddress;
import java.util.List;

public final class Server {

    public static void main(String[] args) {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(eventExecutors)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new ByteToMessageDecoder() {
                                    @Override
                                    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
                                        System.out.println("decode, out.size() == " + out.size());
                                        int intValueByteLength = 4;
                                        while (in.readableBytes() >= intValueByteLength) {
                                            out.add(in.readInt());
                                        }
                                    }
                                })
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        // msg应该是ByteBuf类型
                                        System.out.println("channelRead-1");
                                        System.out.println(msg.getClass());
//                                        super.channelRead(ctx, msg);
                                    }

                                    @Override
                                    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                        System.out.println("channelReadComplete-1");
                                        ctx.channel().writeAndFlush("你好，我是服务端！");
                                    }

                                    @Override
                                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                        System.out.println("异常：" + cause.getMessage());
                                        ctx.channel().close();
                                    }
                                })
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        System.out.println("channelRead-2");
                                        super.channelRead(ctx, msg);
                                    }

                                    @Override
                                    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                        System.out.println("channelReadComplete-2");
                                        super.channelReadComplete(ctx);
                                    }
                                });
                    }
                });

        serverBootstrap.bind(new InetSocketAddress("localhost", 4200)).addListener(f -> {
            if (f.isSuccess()) {
                System.out.println("服务端绑定本地4200端口成功！");
            } else {
                System.err.println("服务端绑定端口失败！关闭连接！");
                eventExecutors.shutdownGracefully().addListener(f2 -> {
                    if (f2.isSuccess()) {
                        System.err.println("关闭成功！");
                    }
                });
            }
        });
    }
}
