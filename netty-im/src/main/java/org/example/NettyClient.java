package org.example;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.example.handlers.ClientHandler;
import org.example.handlers.FirstClientHandler;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
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
                                .addLast(new FirstClientHandler())
                                .addLast(new ClientHandler());
                    }
                });
        connect(bootstrap, 5, 1);
    }

    private static void connect(Bootstrap bootstrap, int retry, int retryTimes) {
        bootstrap.connect().addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else if (retry == 0) {
                System.err.println("重连次数已用完, 放弃连接!");
            } else {
                System.err.println(new Date() + ": 连接失败,第" + retryTimes + "次重连...");
                Runnable reconnect = () -> connect(bootstrap, retry - 1, retryTimes + 1);
                bootstrap.config().group().schedule(reconnect, retryTimes << 2, TimeUnit.SECONDS);
            }
        });
    }
}
