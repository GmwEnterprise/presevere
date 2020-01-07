package cn.gmwenterprise.thinkinjava.io;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomAccessFileTest {

    // 测试随机访问的文件转移速度（多线程）

    public static void main(String[] args) {
        String path = "C:\\Users\\Gmw\\OneDrive\\小丑.官方中字.Joker.2019.HD1080P.X264.AC3.English.CHS.mp4";

        long start = System.currentTimeMillis();

        byRAF(path);

        long end = System.currentTimeMillis();
        System.out.printf("RAF  cost: [%d]%n", end - start);

        start = System.currentTimeMillis();

        byFIS(path);

        end = System.currentTimeMillis();
        System.out.printf("FIS  cost: [%d]%n", end - start);
    }

    private static void byFIS(String path) {
        String target = "E:\\Desktop\\FISJoker.mp4";
        final int bufferSize = 8192;
        try (
            var fin = new BufferedInputStream(new FileInputStream(path), bufferSize);
            var fout = new BufferedOutputStream(new FileOutputStream(target), bufferSize)) {
            int readLen;
            byte[] buf = new byte[bufferSize];
            while ((readLen = fin.read(buf, 0, bufferSize)) != -1) {
                fout.write(buf, 0, readLen);
            }
            fout.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void byRAF(String path) {
        String target = "E:\\Desktop\\RAFJoker.mp4";
        try (
            var from = new RandomAccessFile(path, "rw");
            var to = new RandomAccessFile(target, "rw")) {

            final var once = 0x8000;
            final var total = from.length();

            List<Thread> tasks = Collections.synchronizedList(new ArrayList<>());
            List<Integer> totalLenList = Collections.synchronizedList(new ArrayList<>());

            to.setLength(total);

            int index = 0;
            while (index * once < total) {
                long pos = index++ * once;
                final Thread thread = new Thread(() -> {
                    try {
                        int size = pos + once < total ? once : (int) (total - pos);
                        from.seek(pos);
                        byte[] buf = new byte[size];
                        totalLenList.add(size);
                        from.read(buf);
                        to.seek(pos);
                        to.write(buf);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
                tasks.add(thread);
            }
            for (Thread task : tasks) {
                task.join();
            }
            System.out.println("thread count: " + tasks.size());
            System.out.println(totalLenList.stream().mapToInt(Integer::intValue).sum());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
