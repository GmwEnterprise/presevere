package me.persevere.demo.nio1.channels;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class ServerSocketChannelDemo {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(855));
        SocketChannel sc = ssc.accept();
        System.out.println("成功接收连接请求：");
        System.out.println("  from:[" + sc.getRemoteAddress().toString() + "]");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("请输入内容：");
            String input = scanner.nextLine();
            if (input == null || "".equals(input)) {
                System.out.println("非法输入.");
            } else {
                byte[] bytes = new Message(input).serialize();
                ByteBuffer buf = ByteBuffer.allocate(bytes.length);
                buf.clear();
                buf.put(bytes);
                buf.flip();
                sc.write(buf);
                System.out.println("数据已发送.");
            }
        }
    }
}

class Message {
    public int contentLength;
    public byte[] bytes;

    public Message(String content) {
        bytes = content.getBytes();
        contentLength = bytes.length;
        System.out.println("发送内容长度：" + contentLength);
    }

    /**
     * 序列化字符串，在字符串字节数组前添加四个字节用于表示内容的长度
     */
    public byte[] serialize() {
        byte[] target = new byte[4 + this.bytes.length];
        target[0] = (byte) ((contentLength & 0xFF000000) >>> 24);
        target[1] = (byte) ((contentLength & 0x00FF0000) >>> 16);
        target[2] = (byte) ((contentLength & 0x0000FF00) >>> 8);
        target[3] = (byte) (contentLength & 0x000000FF);
        System.arraycopy(bytes, 0, target, 4, bytes.length);
        return target;
    }

    /*
     * 10进制：72
     *  2进制：00000000 00000000 00000000 01001000
     * 16进制：00 00 00 48
     *
     * (00 00 00 48) & (FF 00 00 00) >>>
     *
     */
}
