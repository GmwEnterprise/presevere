package cn.gmwenterprise.thinkinjava.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试重入锁设定时间尝试获取锁失败后的情景
 */
public class AttemptLocking {
    public static void main(String[] args) throws InterruptedException {
        var al = new AttemptLocking();
        al.untimed();
        al.timed();

        new Thread() {
            { setDaemon(true); }

            @Override
            public void run() {
                al.lock.lock();
                System.out.println("acquired");
            }
        }.start();
        TimeUnit.SECONDS.sleep(1);
        al.untimed();
        al.timed();
    }

    private ReentrantLock lock = new ReentrantLock();

    public void untimed() {
        boolean captured = lock.tryLock();
        try {
            System.out.println("tryLock(): " + captured);
        } finally {
            if (captured) {
                lock.unlock();
            }
        }
    }

    public void timed() {
        boolean captured;
        try {
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("tryLock(2, TimeUnit.SECONDS): " + captured);
        } finally {
            if (captured) {
                lock.unlock();
            }
        }
    }
}
