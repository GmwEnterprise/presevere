package cn.gmwenterprise.test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Task1 implements Runnable {
    @Override
    public void run() {
        try {
            while (true) {
                synchronized (TestYield2.class) {
                    TimeUnit.MILLISECONDS.sleep(300);
                    System.out.println("task_I");
                    TestYield2.class.notifyAll();
                    TestYield2.class.wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Task2 implements Runnable {
    @Override
    public void run() {
        try {
            while (true) {
                synchronized (TestYield2.class) {
                    TimeUnit.MILLISECONDS.sleep(300);
                    System.out.println("task_II");
                    TestYield2.class.notifyAll();
                    TestYield2.class.wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class TestYield2 {
    public static void main(String[] args) {
        var thread1 = new Thread(new Task1());
        var thread2 = new Thread(new Task2());

        thread1.setPriority(Thread.MAX_PRIORITY);
        thread2.setPriority(Thread.MAX_PRIORITY);

        thread2.start();
        thread1.start();
    }
}
