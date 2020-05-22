package me.persevere.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Client {

    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        Selector selector = Selector.open();

        socketChannel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress("localhost", 4000));

        while (true) {
            int count = selector.select();
            System.out.println("select = " + count);
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                System.out.println("hasNext");
                SelectionKey key = keyIterator.next();
                if (key.isWritable()) {
                    System.out.println("isWritable");
                    byte[] bytes = "nextLine".getBytes();
                    ByteBuffer buf = ByteBuffer.allocate(bytes.length);
                    buf.put(bytes);
                    buf.flip();
                    SocketChannel channel = (SocketChannel) key.channel();
                    channel.write(buf);
                } else if (key.isConnectable()) {
                    System.out.println("isConnectable");
                }
                keyIterator.remove();
            }
        }
    }
}
