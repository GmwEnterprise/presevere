package me.persevere.demo.nio1.selectors;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class Demo1 {
    public static void main(String[] args) throws IOException {
        Selector s = Selector.open();

        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false); // 设置为非阻塞

        SelectionKey key = datagramChannel.register(s, SelectionKey.OP_READ);

        int interestOps = key.interestOps();
        int readyOps = key.readyOps();
        SelectableChannel channel = key.channel();
        Selector selector = key.selector();
    }
}
