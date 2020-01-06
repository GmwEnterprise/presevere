package cn.gmwenterprise.thinkinjava.concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DaemonFromFactory implements Runnable {
    @Override
    public void run() {
        try {
            while (true) {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread() + " " + this);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var exec = Executors.newCachedThreadPool(new DaemonThreadFactory());
        for (int i = 0; i < 10; i++) {
            exec.execute(new DaemonFromFactory());
        }
        exec.shutdown();
        System.out.println("All daemons started.");
        TimeUnit.MILLISECONDS.sleep(500);
    }
}
