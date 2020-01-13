package cn.gmwenterprise.thinkinjava.concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Practice21 {
    public static void main(String[] args) {
        Runnable task1 = new Runnable() {
            private synchronized void f() throws InterruptedException {
                wait();
            }

            @Override
            public void run() {
                try {
                    f();
                    System.out.println("msg");
                } catch (InterruptedException e) {
                    System.out.println("Task1 interrupted");
                }
            }
        };

        Runnable task2 = () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                synchronized (task1) {
                    task1.notifyAll();
                }
            } catch (InterruptedException e) {
                System.out.println("Task2 interrupted");
            }
        };

        var exec = Executors.newCachedThreadPool();
        exec.execute(task1);
        exec.execute(task2);
        exec.shutdown();
    }
}
