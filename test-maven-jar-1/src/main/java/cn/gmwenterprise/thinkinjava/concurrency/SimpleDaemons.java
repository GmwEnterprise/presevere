package cn.gmwenterprise.thinkinjava.concurrency;

import java.util.concurrent.TimeUnit;

public class SimpleDaemons implements Runnable {
    @Override
    public void run() {
        try {
            while (true) {
                TimeUnit.SECONDS.sleep(1);
                System.out.printf("%s\t%s%n", Thread.currentThread(), this);
            }
        } catch (InterruptedException e) {
            System.out.println("sleep() interrupted.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            var daemon = new Thread(new SimpleDaemons());
            daemon.setDaemon(true);
            daemon.start();
        }
        System.out.println("All daemons started.");
        TimeUnit.SECONDS.sleep(10);
    }
}
