package cn.gmwenterprise.thinkinjava.concurrency;

import java.util.concurrent.Executors;

public class SimplePriorities implements Runnable {
    private int countDown = 5;
    private volatile double d;
    private int priority;

    public SimplePriorities(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return String.format("p[%d] - %s: %d", priority, Thread.currentThread(), countDown);
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(priority);
        while (true) {
            for (int i = 0; i < 100000; i++) {
                d += (Math.PI + Math.E) / i;
                if (i % 1000 == 0) {
                    Thread.yield();
                }
            }
            System.out.println(this);
            if (--countDown == 0) {
                return;
            }
        }
    }

    public static void main(String[] args) {
        var exec = Executors.newCachedThreadPool();
        var cycle = 50;
        for (int i = 0; i < cycle; i++) {
            exec.execute(new SimplePriorities(Thread.MIN_PRIORITY));
        }
        for (int i = 0; i < cycle / 5; i++) {
            exec.execute(new SimplePriorities(Thread.MAX_PRIORITY));
        }
        exec.shutdown();
    }
}
