package me.persevere.demo;

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

public class Server {

    public static void main(String[] args) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(1024);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(4000));

        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (selector.select() > 0) {
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {
                    System.out.println("isAcceptable");
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    System.out.println("isReadable");
                    SocketChannel channel = (SocketChannel) key.channel();
                    buf.clear();
                    channel.read(buf);
                    buf.flip();
                    List<Byte> list = new ArrayList<>();
                    while (buf.hasRemaining()) {
                        byte b = buf.get();
                        list.add(b);
                    }
//                    byte[] target = new byte[list.size()];
//                    for (int i = 0; i < list.size(); i++) {
//                        target[i] = list.get(i);
//                    }
                    System.out.println("接收信息：" + list);
                }
                keyIterator.remove();
            }
        }
    }
}
