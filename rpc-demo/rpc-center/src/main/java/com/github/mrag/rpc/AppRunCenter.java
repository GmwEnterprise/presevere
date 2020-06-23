package com.github.mrag.rpc;

import com.github.mrag.rpc.pipeline.ChannelInitializerForRpc;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Gmw
 */
@SpringBootApplication
public class AppRunCenter {
    private static final Logger log = LoggerFactory.getLogger(AppRunCenter.class);

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(AppRunCenter.class);
    }

    private final EventLoopGroup eventExecutors = new NioEventLoopGroup();

    @PostConstruct
    public void runNetty() {

        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(eventExecutors)
                .localAddress(4433)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializerForRpc());

        bootstrap.bind().addListener(f -> {
            if (f.isSuccess()) {
                log.info("netty is running with port[4433].");
            } else {
                SpringApplication.exit(applicationContext);
            }
        });
    }

    @PreDestroy
    public void exit() {
        log.info("netty is exiting...");
        eventExecutors.shutdownGracefully();
    }
}
