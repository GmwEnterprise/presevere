package me.persevere.demo.nio1.channels;

import com.google.common.primitives.Bytes;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class SocketChannelDemo {

    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("localhost", 855));
        System.out.println("成功发送连接请求：");
        System.out.println("  to:[" + sc.getRemoteAddress().toString() + "]");

        ByteBuffer buf = ByteBuffer.allocate(16);
        int read = sc.read(buf);
        List<Byte> bytes = new ArrayList<>();
        int contentLength = 0;
        while (read != -1) {
            buf.flip();
            while (buf.hasRemaining()) {
                bytes.add(buf.get());
                if (contentLength == 0 && bytes.size() == 4) {
                    // 接收完报文的长度内容
                    int len1 = bytes.get(0).intValue() << 24;
                    int len2 = bytes.get(1).intValue() << 16;
                    int len3 = bytes.get(2).intValue() << 8;
                    int len4 = bytes.get(3).intValue();
                    int len = len1 | len2 | len3 | len4;
                    System.out.println("收到报文内容长度：" + len);
                    contentLength = len;
                    bytes.clear();
                }

                if (contentLength != 0 && bytes.size() == contentLength) {
                    // 已接收完一段报文
                    String content = new String(Bytes.toArray(bytes));
                    System.out.println("接收信息：" + content);
                    bytes.clear();
                    contentLength = 0;
                }
            }
            buf.compact();
            read = sc.read(buf);
        }
    }
}
