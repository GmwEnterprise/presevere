package me.persevere.demo.nio1.channels;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TcpTest2 {

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
                System.out.println("请输入客户端即将发送的数据");
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
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        Selector selector = Selector.open();
        sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        sc.connect(new InetSocketAddress(5001));

        while (selector.select() > 0) {
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isReadable()) {
                    SocketChannel scc = (SocketChannel) key.channel();
                    ByteBuffer receiverBuf = ByteBuffer.allocate(512);
                    int read = scc.read(receiverBuf);
                    if (read != -1) {
                        ArrayList<Byte> bytes = new ArrayList<>();
                        while (receiverBuf.hasRemaining()) {
                            bytes.add(receiverBuf.get());
                        }
                        byte[] bytes1 = new byte[bytes.size()];
                        for (int i = 0; i < bytes.size(); i++) {
                            bytes1[i] = bytes.get(i);
                        }
                        String msg = new String(bytes1);
                        readableMsgList.add(msg);
                    }
                } else if (key.isWritable()) {
                    SocketChannel scc = (SocketChannel) key.channel();
                    // write
                    if (!writableMsgList.isEmpty()) {
                        String msg = writableMsgList.remove(0);
                        byte[] msgBytes = msg.getBytes();
                        ByteBuffer buf = ByteBuffer.allocate(msgBytes.length);
                        buf.put(msgBytes);
                        buf.flip();
                        scc.write(buf);
                        buf.clear();
                    }
                }
                keyIterator.remove();
            }
        }
    }
}
