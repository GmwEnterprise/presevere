package me.persevere.demo.nio1.channels;

import com.google.common.primitives.Bytes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class FileChannelExample {

    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile("D:\\文本文件.txt", "rw");
        FileChannel fc = file.getChannel();

        System.out.println(fc.size());

        ByteBuffer buf = ByteBuffer.allocate(9);
        int read = fc.read(buf);
        while (read != -1) {
            buf.flip();
            List<Byte> bytes = new ArrayList<>(8);
            while (buf.hasRemaining()) {
                bytes.add(buf.get());
            }
            System.out.println(new String(Bytes.toArray(bytes)));
            buf.compact();
            read = fc.read(buf);
        }

        FileChannel truncateChannel = fc.truncate(9);
        System.out.println(fc.size());
        System.out.println(truncateChannel.size());

//        truncateChannel.close();

        fc.close();
        file.close();
    }
}
