package org.example.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.example.Console;

public class ServerDemo {

    static void bind(ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(port).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                Console.log("成功绑定端口：" + port);
            } else {
                bind(serverBootstrap, port + 1);
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup parentGroup = new NioEventLoopGroup();
        NioEventLoopGroup childGroup = new NioEventLoopGroup();
        serverBootstrap.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new FirstServerHandler());
                    }
                });
        bind(serverBootstrap, 8080);
        Console.log("启动...");
    }

    @ChannelHandler.Sharable
    public static class FirstServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            // 接收
            ByteBuf buf = (ByteBuf) msg;
            byte[] dst = new byte[buf.readableBytes()];
            buf.readBytes(dst);
            // Console.log("收到数据: " + buf.toString(CharsetUtil.UTF_8));
            Console.log("收到数据: " + new String(dst));

            // 回复
            String content = "你好，我是服务端";
            Console.log("服务端回复数据：" + content);
            ByteBuf returnBuf
                    // = Unpooled.copiedBuffer(content.getBytes()); // UnpooledHeapByteBuf(ridx:0,widx:24,cap:24/24)
                    = ctx.alloc().buffer(); // PooledUnsafeDirectByteBuf(ridx:0,widx:0,cap:256)
            returnBuf.writeBytes(content.getBytes());

            if (returnBuf.hasArray()) {
                Console.log("return buf has array.");
            }

            ctx.channel().writeAndFlush(returnBuf);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            Console.log("异常触发连接关闭：" + cause.getMessage());
            ctx.close();
        }
    }
}
