package org.example.runner;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.example.handlers.PacketDecoder;
import org.example.handlers.PacketEncoder;
import org.example.handlers.Spliter;
import org.example.handlers.client.LoginResponseHandler;
import org.example.handlers.client.MessageResponseHandler;
import org.example.packet.MessageRequestPacket;
import org.example.packet.PacketCodeC;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    public static void main(String[] args) {
        runClient();
    }

    public static void runClient() {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .attr(AttributeKey.newInstance("clientName"), "nettyClient")
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .group(worker)
                .remoteAddress("localhost", 8080)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new Spliter())
                                .addLast(new PacketDecoder())
                                .addLast(new LoginResponseHandler())
                                .addLast(new MessageResponseHandler())
                                .addLast(new PacketEncoder());
                    }
                });
        connect(bootstrap, 5, 1);
    }

    private static void connect(Bootstrap bootstrap, int retry, int retryTimes) {
        bootstrap.connect().addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功! 启动控制台线程...");
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.err.println("重连次数已用完, 放弃连接!");
            } else {
                System.err.println(new Date() + ": 连接失败,第" + retryTimes + "次重连...");
                Runnable reconnect = () -> connect(bootstrap, retry - 1, retryTimes + 1);
                bootstrap.config().group().schedule(reconnect, retryTimes << 2, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                System.out.println("输入消息发送至服务端: ");
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();

                // TODO 小册16章末尾

                MessageRequestPacket packet = new MessageRequestPacket();
                packet.setMessage(line);
                ByteBuf buf = PacketCodeC.INSTANCE.encode(packet, channel.alloc());
                channel.writeAndFlush(buf);
            }
        }).start();
    }
}
