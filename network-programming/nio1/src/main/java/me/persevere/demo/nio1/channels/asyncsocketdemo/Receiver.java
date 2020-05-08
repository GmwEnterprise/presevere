package me.persevere.demo.nio1.channels.asyncsocketdemo;

import com.google.common.primitives.Bytes;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Receiver {
    private static List<String> logs = Collections.synchronizedList(new LinkedList<>() {
        @Override
        public boolean add(String s) {
            return super.add(LocalDateTime.now().toString() + " --> " + s);
        }
    });

    public static void main(String[] args) throws IOException, InterruptedException {
        RandomAccessFile file = new RandomAccessFile("D:\\receiver.log", "rw");
        FileChannel fc = file.getChannel();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(() -> { // TODO
            while (true) {
                if (logs.size() > 0) {
                    String log = logs.remove(0);

                }
            }

        });

        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(8555));
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer readBuf = ByteBuffer.allocate(128);
        ByteBuffer writeBuf = ByteBuffer.allocate(128);

        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    // 接收到了新的连接请求
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    logs.add("连接建立请求已接受：from[" + sc.getRemoteAddress() + "]");
                } else if (key.isReadable()) {
                    // 可读取数据
                    SocketChannel channel = (SocketChannel) key.channel();
                    readBuf.clear();
                    int read = channel.read(readBuf);
                    List<Byte> byteList = new ArrayList<>();
                    while (read != -1) {
                        readBuf.flip();
                        while (readBuf.hasRemaining()) {
                            byteList.add(readBuf.get());
                        }
                        readBuf.compact();
                        read = channel.read(readBuf);
                    }
                    logs.add("读取信息From[" + channel.getRemoteAddress() + "]：" + new String(Bytes.toArray(byteList)));
                } else if (key.isWritable()) {
                    // 可写入数据

                }

                TimeUnit.MILLISECONDS.sleep(100);
            }
        }
    }
}
