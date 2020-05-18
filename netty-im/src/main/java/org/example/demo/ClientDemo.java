package org.example.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.example.Console;

import java.util.concurrent.TimeUnit;

public class ClientDemo {

    private static void connect(Bootstrap bootstrap, int retry, long delay, int retryTimes) {
        bootstrap.connect().addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                Console.log("连接成功！");
            } else if (retryTimes == 0) {
                Console.log("连接失败。");
            } else {
                Console.log("连接失败，" + delay + "秒后第" + retry + "次重连...");
                bootstrap.config().group().schedule(
                        () -> connect(bootstrap, retry + 1, delay << 1, retryTimes - 1),
                        delay, TimeUnit.SECONDS);
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .remoteAddress("localhost", 8080)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        Console.log("初始化...");
                        ch.pipeline().addLast(new FirstClientHandler());
                    }
                });
        connect(bootstrap, 1, 2, 3);
    }

    @ChannelHandler.Sharable
    public static class FirstClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            String content = "Hello ! My name is Gmw. ";
            Console.log("客户端发送数据：" + content);
            ByteBuf buf = ctx
                    .alloc() // 获取byteBuf的内存管理器
                    .buffer(); // 分配一个byteBuf
            buf.writeBytes(content.getBytes());

            // 写数据
            // ctx.channel().writeAndFlush(buf);
            ctx.writeAndFlush(buf).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    Console.log("发送成功.");
                } else {
                    Console.log("发送失败：");
                }
            });
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = (ByteBuf) msg;
            Console.log("收到回复：" + buf.toString(CharsetUtil.UTF_8));
        }
    }
}
