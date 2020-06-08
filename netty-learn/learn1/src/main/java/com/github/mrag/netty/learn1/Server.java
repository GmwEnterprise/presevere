package com.github.mrag.netty.learn1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

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
                        ch.pipeline().addLast(
                                new ByteToMessageDecoder() {
                                    @Override
                                    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
                                        int readableBytes = in.readableBytes();
                                        System.out.println(in.hashCode() + " -> 可读字节数：" + readableBytes);
                                        if (readableBytes >= 4) {
                                            byte one = in.readByte();
                                            byte two = in.readByte();
                                            byte three = in.readByte();
                                            byte four = in.readByte();
                                            StringBuilder sb = new StringBuilder()
                                                    .append("[")
                                                    .append(one).append(", ")
                                                    .append(two).append(", ")
                                                    .append(three).append(", ")
                                                    .append(four).append("]");
                                            out.add(sb);
                                            out.add(Integer.parseInt(Math.abs(one) + "" + Math.abs(two) + "" + Math.abs(three) + "" + Math.abs(four)));
                                        } else if (readableBytes == 2) {
                                            throw new TooLongFrameException("长度超出要求");
                                        }
                                    }
                                })
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        System.out.println(" >> channelInboundHandler[typeof msg = " + msg.getClass() + "]");
                                        super.channelRead(ctx, msg);
                                    }
                                })
                                .addLast(new SimpleChannelInboundHandler<StringBuilder>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, StringBuilder msg) throws Exception {
                                        System.out.println("STR VALUE: " + msg);
                                    }
                                })
                                .addLast(new SimpleChannelInboundHandler<Integer>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, Integer msg) throws Exception {
                                        System.out.println("INT VALUE: " + msg);
                                    }
                                })
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                        System.err.println("打印异常：" + cause.getMessage() + "\n打印调用栈：");
                                        cause.printStackTrace();
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
