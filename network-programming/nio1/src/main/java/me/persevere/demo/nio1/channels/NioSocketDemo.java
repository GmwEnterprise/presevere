package me.persevere.demo.nio1.channels;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class NioSocketDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(true);
        sc.connect(new InetSocketAddress("localhost", 8008));
        Random rand = new Random();
        ByteBuffer buf = ByteBuffer.allocate(1024);
        for (; ; ) {
            buf.clear();
            String content = "Number[" + rand.nextInt() + "]";
            System.out.println("发送数据：" + content);
            buf.put(content.getBytes());
            buf.flip();
            sc.write(buf);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
