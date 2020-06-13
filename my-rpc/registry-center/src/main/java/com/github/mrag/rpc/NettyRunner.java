package com.github.mrag.rpc;

import com.github.mrag.rpc.codec.JacksonCodec;
import com.github.mrag.rpc.packet.Packet;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

@Component
public class NettyRunner {
    private static final Logger log = LoggerFactory.getLogger(NettyRunner.class);
    private final EventLoopGroup eventExecutors = new NioEventLoopGroup();

    private final int port;
    private final ByteToMessageCodec<Packet> codec;

    public NettyRunner(RpcProperties rpcProperties) {
        port = rpcProperties.getPort();
        RpcProperties.Serialization serialization = rpcProperties.getDefaultSerialization();
        switch (serialization) {
            case JSON:
            case PROTOBUF:
                // TODO ProtocolBuffer序列化待配置
            default:
                codec = new JacksonCodec();
        }
    }

    @PostConstruct
    public void runNetty() {
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(eventExecutors)
                .localAddress(new InetSocketAddress(4222))
                .channel(NioServerSocketChannel.class)
                .handler(new ChannelInitializer<ServerSocketChannel>() {
                    @Override
                    protected void initChannel(ServerSocketChannel ch) throws Exception {
                        log.info("Netty服务本地初始化...");
                    }
                })
                .childHandler(new RemoteChannelInitializer(codec));

        bootstrap.bind().addListener(f -> {
            if (f.isSuccess()) {
                log.info("Netty服务绑定本地端口{}成功", port);
            } else {
                log.error("Netty服务绑定本地端口{}失败", port);
            }
        });
    }

    @PreDestroy
    public void clean() {
        eventExecutors.shutdownGracefully().addListener(f -> {
            if (f.isSuccess()) {
                log.info("eventExecutors已关闭");
            }
        });
    }
}
