package cn.gmwenterprise.test;

import java.util.concurrent.TimeUnit;

public class Test4 implements Runnable {
    public synchronized void a() {
        try {
            wait();
            System.out.println("After wait");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        a();
    }

    public static void main(String[] args) throws InterruptedException {
        var test4 = new Test4();
        new Thread(test4).start();

        TimeUnit.SECONDS.sleep(2);
        synchronized (test4) {
            test4.notify();
        }
    }
}
