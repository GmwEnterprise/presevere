package me.persevere.demo.nio1.channels;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelExample {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("https://www.baidu.com/", 80));

        ByteBuffer buf = ByteBuffer.allocateDirect(2048);
        // TODO

        sc.close();
    }
}
