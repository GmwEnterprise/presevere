package me.persevere.demo.nio1.selectors;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class SelectorClient {

    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);

        Selector select = Selector.open();
        SelectionKey key = sc.register(select, SelectionKey.OP_CONNECT);
        sc.connect(new InetSocketAddress("localhost", 5000));

        while (true) {
            if (key.isConnectable()) {
                System.out.println("可连接的");
                break;
            }
        }
    }
}
