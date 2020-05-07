package me.persevere.demo.nio1.channels;

import com.google.common.primitives.Bytes;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class SocketChannelExample {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel
                .open(new InetSocketAddress("www.baidu.com", 80));

        ByteBuffer buf = ByteBuffer.allocateDirect(2048);
        int read = sc.read(buf);
        List<Byte> bytes = new ArrayList<>();
        while (read != -1) {
            buf.flip();
            while (buf.hasRemaining()) {
                bytes.add(buf.get());
            }
            buf.compact();
            read = sc.read(buf);
        }
        sc.close();

        byte[] arr = Bytes.toArray(bytes);
        System.out.println(new String(arr));
    }
}
