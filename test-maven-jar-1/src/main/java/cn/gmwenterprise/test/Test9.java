package cn.gmwenterprise.test;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test9 {

    static int counter = 0;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var exec = Executors.newCachedThreadPool();
        var submit1 = exec.submit(() -> {
            var integers = new ArrayList<Integer>();
            for (int i = 0; i < 10000; i++) {
                integers.add(++counter);
            }
            return integers;
        });
        var submit2 = exec.submit(() -> {
            var integers = new ArrayList<Integer>();
            for (int i = 0; i < 10000; i++) {
                integers.add(++counter);
            }
            return integers;
        });
        var submit3 = exec.submit(() -> {
            var integers = new ArrayList<Integer>();
            for (int i = 0; i < 10000; i++) {
                integers.add(++counter);
            }
            return integers;
        });
        exec.shutdown();
        var integers1 = submit1.get();
        var integers2 = submit2.get();
        var integers3 = submit3.get();
        integers1.forEach(item -> {
            if ((double) item % 1 != 0L) {
                System.out.println(item);
            }
        });
        integers2.forEach(item -> {
            if ((double) item % 1 != 0L) {
                System.out.println(item);
            }
        });
        integers3.forEach(item -> {
            if ((double) item % 1 != 0L) {
                System.out.println(item);
            }
        });
        System.out.println("done. " + counter);
    }
}
