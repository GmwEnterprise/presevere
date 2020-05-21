package me.persevere.demo.nio1.channels;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TcpTest1 {

    static final List<String> writableMsgList = Collections.synchronizedList(new LinkedList<>());
    static final List<String> readableMsgList = Collections.synchronizedList(new LinkedList<>());

    public static void main(String[] args) throws IOException {
        ExecutorService exec = Executors.newCachedThreadPool();
        Scanner scanner = new Scanner(System.in);
        exec.execute(() -> {
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("请输入服务端即将发送的数据");
                String nextLine = scanner.nextLine();
                writableMsgList.add(nextLine);
                if (!readableMsgList.isEmpty()) {
                    Iterator<String> iterator = readableMsgList.iterator();
                    while (iterator.hasNext()) {
                        System.out.println("接收信息：" + iterator.next());
                        iterator.remove();
                    }

                }
            }
        });

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(5001));

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();


                    System.out.println("接收到建立连接请求");
                    SocketChannel sc = serverSocketChannel.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                } else if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();


                    // read
                    ByteBuffer dst = ByteBuffer.allocate(512);
                    int read = sc.read(dst);

                    ArrayList<Byte> bytes = new ArrayList<>();
                    if (read != -1) {
                        dst.flip();
                        while (dst.hasRemaining()) {
                            byte b = dst.get();
                            bytes.add(b);
                        }
                        byte[] bytes1 = new byte[bytes.size()];
                        for (int i = 0; i < bytes.size(); i++) {
                            bytes1[i] = bytes.get(i);
                        }
                        String msg = new String(bytes1);
                        readableMsgList.add(msg);
                    }

                } else if (key.isWritable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    // write
                    if (!writableMsgList.isEmpty()) {
                        String msg = writableMsgList.remove(0);
                        byte[] msgBytes = msg.getBytes();
                        ByteBuffer buf = ByteBuffer.allocate(msgBytes.length);
                        buf.put(msgBytes);
                        buf.flip();
                        sc.write(buf);
                        buf.clear();
                    }
                }

                iterator.remove();
            }


        }
    }
}
