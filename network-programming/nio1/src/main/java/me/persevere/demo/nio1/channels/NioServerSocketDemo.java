package me.persevere.demo.nio1.channels;

import com.google.common.primitives.Bytes;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class NioServerSocketDemo {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(true);
        ssc.bind(new InetSocketAddress(8008));
        SocketChannel sc = ssc.accept();
        System.out.println("远程连接已建立.");
        ByteBuffer buf = ByteBuffer.allocate(512);
        for (; ; ) {
            buf.compact();
            int read = sc.read(buf);
            if (read != 1) {
                ArrayList<Byte> bytes = new ArrayList<>();
                buf.flip();
                while (buf.hasRemaining()) {
                    bytes.add(buf.get());
                }
                System.out.println("接收数据：" + new String(Bytes.toArray(bytes)));
            }
        }
    }
}
