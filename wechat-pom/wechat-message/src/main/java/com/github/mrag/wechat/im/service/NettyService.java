package com.github.mrag.wechat.im.service;

import com.github.mrag.wechat.im.handlers.IdleStateChildHandlerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class NettyService {
    private static final Logger log = LoggerFactory.getLogger(NettyService.class);

    private final ServerBootstrap bootstrap;
    private final EventLoopGroup eventExecutors;
    private final int tcpPort = 4201;

    public NettyService() {

        eventExecutors = new NioEventLoopGroup();

        bootstrap = new ServerBootstrap()
                .localAddress(tcpPort)
                .channel(NioServerSocketChannel.class)
                .group(eventExecutors)
                .childHandler(new IdleStateChildHandlerInitializer());
    }

    @PostConstruct
    public void bind() {
        bootstrap.bind().addListener(f -> {
            if (f.isSuccess()) {
                log.info("TCP服务绑定本地端口[{}]成功!", tcpPort);
            } else {
                log.error("TCP服务绑定本地端口[" + tcpPort + "]失败! 原因: {}", f.cause().getMessage(), f.cause());
                System.exit(1);
            }
        });
    }

    /**
     * @return 对服务所使用的ServerBootstrap的深拷贝（除了EventLoopGroup）, 通常用于临时操作，共享线程池
     */
    public ServerBootstrap cloneBootstrap() {
        return bootstrap.clone();
    }

    @PreDestroy
    public void close() {
        eventExecutors.shutdownGracefully().addListener(f -> {
            if (f.isSuccess()) {
                log.info("TCP服务已关闭");
            } else {
                log.error("TCP服务关闭失败! ", f.cause());
            }
        });
    }

    public void infoLog(String msg, Object... params) {
        log.info(msg, params);
    }

    public void infoLog(String msg) {
        log.info(msg);
    }

    public void errorLog(String errorMsg, Throwable cause) {
        log.error(errorMsg, cause);
    }
}
