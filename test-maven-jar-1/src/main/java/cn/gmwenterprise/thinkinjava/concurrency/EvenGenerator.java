package cn.gmwenterprise.thinkinjava.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EvenGenerator extends IntGenerator {
    private int currentEvenValue = 0;

    private final Lock lock = new ReentrantLock();

    @Override
    public int next() {
        // java中，递增并不是原子性操作
        lock.lock();
        try {
            ++currentEvenValue;
//            Thread.yield();
            ++currentEvenValue;

            // 如果把return放到unlock之后执行，仍然会出现奇数错误
            return currentEvenValue;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        /*
        当next方法设置为同步时，由于这个方法持有对象锁
        即便在方法中调用了Thread.yield()使其放弃当前CPU时间片，
        其他线程无法获取到执行该方法需要的锁
        所以该方法还是会继续执行
         */
        EvenChecker.test(new EvenGenerator());
    }
}
