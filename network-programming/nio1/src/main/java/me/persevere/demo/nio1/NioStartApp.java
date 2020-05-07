package me.persevere.demo.nio1;

import com.google.common.primitives.Bytes;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class NioStartApp {

    public static void main(String[] args) throws IOException {
        RandomAccessFile fileStream = new RandomAccessFile("D:\\文本文件.txt", "rw");
        FileChannel fileChannel = fileStream.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(256);
        int read = fileChannel.read(buf);

        ArrayList<Byte> bytes = new ArrayList<>();
        int count = 0;
        while (read != -1) {
            System.out.println("Read: " + read);
            buf.flip(); // write mode  ->  read mode; make buf ready to read

            while (buf.hasRemaining()) {
                bytes.add(buf.get()); // get 1 byte
                System.out.printf("capacity[%d], position[%d], limit[%d]\n",
                        buf.capacity(), buf.position(), buf.limit());
                if (buf.position() == 15) {
                    buf.position(0);
                    count++;
                }
                if (count > 3) {
                    break;
                }
            }
            buf.clear(); // make buf ready for writing
            read = fileChannel.read(buf);
        }
        fileStream.close();
        System.out.println(new String(Bytes.toArray(bytes)));
    }
}
