package cn.gmwenterprise.test;

import java.util.ArrayList;

public class Test8 {

    public static void main(String[] args) throws InterruptedException {
        var threads = new ArrayList<Thread>(10);
        for (int i = 0; i < 100; i++) {
            var low = new Low();
            var high = new High();
            threads.add(low);
            threads.add(high);
            high.start();
            low.start();
        }
        Thread.sleep(5000);
        threads.forEach(Thread::interrupt);
    }
}

class High extends Thread {
    High() {
        super();
        setPriority(MAX_PRIORITY);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                synchronized (Test8.class) {
                    sleep(100);
                }
                System.out.println("High priority");
                Thread.sleep(0);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Low extends Thread {
    Low() {
        super();
        setPriority(MIN_PRIORITY);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                synchronized (Test8.class) {
                    sleep(100);
                }
                System.out.println("Low priority");
                Thread.yield();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}