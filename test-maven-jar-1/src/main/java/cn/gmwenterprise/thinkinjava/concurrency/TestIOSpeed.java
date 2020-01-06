package cn.gmwenterprise.thinkinjava.concurrency;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestIOSpeed {

    private static final String ROOT = "C:\\Users\\Gmw\\OneDrive\\PDF电子书\\";
    private static final String[] FILE_ARR = new String[]{
        "Java 编程思想(第4版).pdf", "Docker即学即用.pdf", "Spring5高级编程.pdf",
        "Java EE互联网轻量级框架整合开发.pdf", "《Spring微服务实战 in action 中文版》_陈文辉.pdf"
    };

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var start = System.currentTimeMillis();
        synchronizedFunc();
        System.out.printf("synchronized function spent %dms.%n", System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        concurrencyFunc();
        System.out.printf("concurrency function spent %dms.%n", System.currentTimeMillis() - start);
    }

    private static void concurrencyFunc() throws ExecutionException, InterruptedException {
        var exec = Executors.newCachedThreadPool();
        var futures = new ArrayList<Future<Integer>>();
        for (String pdf : FILE_ARR) {
            futures.add(exec.submit(() -> {
                try (var fin = new FileInputStream(new File(ROOT + pdf))) {
                    byte[] buf = new byte[2048];
                    while (fin.read(buf) != -1) {
                        // do nothing
                    }
                    return 1;
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    return 0;
                }
            }));
        }
        exec.shutdown();
        for (Future<Integer> future : futures) {
            future.get();
        }
    }

    private static void synchronizedFunc() {
        for (String pdf : FILE_ARR) {
            try (var fin = new FileInputStream(new File(ROOT + pdf))) {
                byte[] buf = new byte[2048];
                while (fin.read(buf) != -1) {
                    // do nothing
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
