package com.github.mrag.rpc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

@Component
@ConfigurationProperties("netty")
public class NettyRunner {
    private static final Logger log = LoggerFactory.getLogger(NettyRunner.class);

    private final EventLoopGroup parentGroup, childGroup;

    private int port;

    public NettyRunner() {
        this.parentGroup = new NioEventLoopGroup();
        this.childGroup = new NioEventLoopGroup();
    }

    @PostConstruct
    public void runNetty() {
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(parentGroup, childGroup)
                .localAddress(new InetSocketAddress(4222))
                .channel(NioServerSocketChannel.class)
                .handler(new ChannelInitializer<ServerSocketChannel>() {
                    @Override
                    protected void initChannel(ServerSocketChannel ch) throws Exception {
                        log.info("Netty服务本地初始化...");
                    }
                })
                .childHandler(new RemoteChannelInitializer());

        bootstrap.bind().addListener(f -> {
            if (f.isSuccess()) {
                log.info("Netty服务绑定本地端口{}成功", port);
            }
        });
    }

    @PreDestroy
    public void clean() {
        parentGroup.shutdownGracefully().addListener(f -> {
            if (f.isSuccess()) {
                log.info("parentGroup已关闭");
            }
        });
        childGroup.shutdownGracefully().addListener(f -> {
            if (f.isSuccess()) {
                log.info("childGroup已关闭");
            }
        });
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
