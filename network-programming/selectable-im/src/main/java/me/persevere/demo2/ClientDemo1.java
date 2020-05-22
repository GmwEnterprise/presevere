package me.persevere.demo2;

import com.google.common.primitives.Bytes;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientDemo1 {
    private static final LinkedList<String> msgList = new LinkedList<>();

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        ExecutorService exec = Executors.newCachedThreadPool();

        ByteBuffer buf = ByteBuffer.allocate(1024);
        Selector selector = Selector.open();

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress(8080));

        while (true) {
            int select = selector.select();
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();

                if (key.isWritable()) {
                    System.out.println("即将写数据：");
                    String msg = msgList.pop();
                    buf.clear();
                    buf.put(msg.getBytes());
                    buf.flip();
                    SocketChannel channel = (SocketChannel) key.channel();
                    channel.write(buf);
                    // 写完后注销掉写事件
                    channel.register(selector, key.interestOps() & ~SelectionKey.OP_WRITE);


                } else if (key.isConnectable()) {
                    System.out.println("isConnectable");
                    SocketChannel channel = (SocketChannel) key.channel();
                    channel.register(selector, SelectionKey.OP_READ);

                    exec.execute(() -> {
                        while (true) {
                            System.out.println("请输入即将发送的数据：");
                            String input = scanner.nextLine();
                            msgList.push(input);
                            try {
                                channel.register(selector, SelectionKey.OP_WRITE);
                            } catch (ClosedChannelException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else if (key.isReadable()) {
                    System.out.print("收到数据：");
                    SocketChannel channel = (SocketChannel) key.channel();
                    buf.clear();
                    channel.read(buf);
                    buf.flip();

                    ArrayList<Byte> list = new ArrayList<>();
                    while (buf.hasRemaining()) {
                        list.add(buf.get());
                    }
                    System.out.println(new String(Bytes.toArray(list)));
                }

                keyIterator.remove();
            }
        }
    }
}
