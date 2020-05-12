package me.persevere.demo.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
    public static void main(String[] args) throws Exception {
        new EchoServer(8080).start();
    }

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        EchoServerHandler echoServerHandler = new EchoServerHandler();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            // 服务器引导实例
            ServerBootstrap b = new ServerBootstrap();
            b.group(eventLoopGroup)
                    // 指定使用的NIO传输Channel类型
                    .channel(NioServerSocketChannel.class)
                    // 绑定本地端口
                    .localAddress(port)
                    // 添加一个EchoServerHandler到Channel的ChannelPipeline
                    // 因为Sharable注解，所有的Channel可以复用同一个Handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(echoServerHandler);
                        }
                    });

            // bind()方法会异步绑定服务器；调用sync则变为同步并阻塞
            ChannelFuture f = b.bind().sync();

            // 阻塞等待所有channel的关闭
            f.channel().closeFuture().sync();
        } finally {
            // 同步释放资源
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
}
