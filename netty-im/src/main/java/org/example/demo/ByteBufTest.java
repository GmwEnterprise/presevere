package org.example.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBufTest {
    public static void main(String[] args) {
        System.out.println("堆内存上创建非池化buf缓冲区");
        ByteBuf buf = Unpooled.buffer(10);
        System.out.println("是否含有支撑数组（是否是堆缓冲区）：" + buf.hasArray());

        System.out.printf("readerIndex: %d, writerIndex: %d, capacity: %d%n",
                buf.readerIndex(), buf.writerIndex(), buf.capacity());

        System.out.println("写入6个字节");
        buf.writeBytes(new byte[]{40, 41, 42, 43, 44, 45});
        System.out.printf("readerIndex: %d, writerIndex: %d, capacity: %d%n",
                buf.readerIndex(), buf.writerIndex(), buf.capacity());

        System.out.println("读取2个字节");
        byte[] bytes = new byte[2];
        buf.readBytes(bytes);
        System.out.println("read bytes: [" + bytes[0] + ", " + bytes[1] + "]");
        System.out.printf("readerIndex: %d, writerIndex: %d, capacity: %d%n",
                buf.readerIndex(), buf.writerIndex(), buf.capacity());

        for (int i = 0; i < buf.capacity(); i++) {
            System.out.print(buf.getByte(i) + "   ");
        }
        System.out.println();

        System.out.println("调用discardReadBytes");
        buf.discardReadBytes();
        System.out.printf("readerIndex: %d, writerIndex: %d, capacity: %d%n",
                buf.readerIndex(), buf.writerIndex(), buf.capacity());

        for (int i = 0; i < buf.capacity(); i++) {
            System.out.print(buf.getByte(i) + "   ");
        }
        System.out.println();
    }
}
