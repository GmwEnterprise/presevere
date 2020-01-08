package cn.gmwenterprise.test;

import java.util.concurrent.TimeUnit;

public class Test3 {

    public static void main(String[] args) throws InterruptedException {
        var thread = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Interrupt !");
                    return;
                } else {
                    Thread.yield();
                }
            }
        });

        thread.start();
        TimeUnit.SECONDS.sleep(2);
        thread.interrupt();
    }
}
