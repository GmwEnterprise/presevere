package cn.gmwenterprise.test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestYield {
    private static volatile int count = 20;

    public static void main(String[] args) {
        var exec = Executors.newCachedThreadPool();
        exec.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (this) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (count-- > 0) {
                            System.out.println("task_1, count = " + count);
                        } else {
                            break;
                        }
                    }
                    Thread.yield();
                }
            }
        });
        exec.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (this) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (count-- > 0) {
                            System.out.println("task_2, count = " + count);
                        } else {
                            break;
                        }
                    }
                    Thread.yield();
                }
            }
        });
        exec.shutdown();
    }
}
