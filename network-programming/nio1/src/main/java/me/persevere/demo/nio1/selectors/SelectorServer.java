package me.persevere.demo.nio1.selectors;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class SelectorServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(5000));
        Selector selector = Selector.open();
        SelectionKey register = ssc.register(selector, SelectionKey.OP_ACCEPT);
    }
}
