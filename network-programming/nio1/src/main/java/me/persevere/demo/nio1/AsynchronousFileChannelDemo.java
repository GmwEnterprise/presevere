package me.persevere.demo.nio1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class AsynchronousFileChannelDemo {

    public static void main(String[] args) throws IOException {
        AsynchronousFileChannel asyncFileChannel = AsynchronousFileChannel.open(
                Paths.get("D:\\文本文件.txt"),
                StandardOpenOption.READ,
                StandardOpenOption.WRITE);
        ByteBuffer buffer = ByteBuffer.allocate(64);
        Future<Integer> operation = asyncFileChannel.read(buffer, 0);
        while (!operation.isDone()) {
        }

        buffer.flip();
        byte[] data = new byte[buffer.limit()];
        buffer.get(data);
        System.out.println(new String(data));
        buffer.clear();
    }
}
