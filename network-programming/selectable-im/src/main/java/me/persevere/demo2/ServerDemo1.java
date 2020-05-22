package me.persevere.demo2;

import com.google.common.primitives.Bytes;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServerDemo1 {

    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(8080));
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (selector.select() > 0) {
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();

                if (key.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel channel = serverSocketChannel.accept();
                    System.out.printf("建立连接：remote[%s]%n", channel.getRemoteAddress());
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);
                }

                else if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    buffer.clear();
                    int read = channel.read(buffer);
                    if (read != -1) {
                        buffer.flip();
                        List<Byte> list = new ArrayList<>();
                        while (buffer.hasRemaining()) {
                            list.add(buffer.get());
                        }
                        byte[] bytes = Bytes.toArray(list);
                        System.out.printf("接收信息：%s%n", new String(bytes));
                    }
                }

                keyIterator.remove();
            }
        }
    }
}
