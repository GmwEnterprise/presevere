package cn.gmwenterprise.demo;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 实现一个阻塞队列
 */
public class MyBlockingQueue<E> {
    private int maxSize;

    /**
     * 通过构造函数决定该队列的边界
     *
     * @param maxSize 最大容量
     */
    public MyBlockingQueue(int maxSize, long maxWaiting, TimeUnit timeUnit) {
        this.maxSize = maxSize;
    }

    /**
     * 核心队列
     */
    private LinkedList<E> queue = new LinkedList<>();
    /**
     * 锁
     */
    private Lock putLock = new ReentrantLock();
    private Condition putCondition = putLock.newCondition();
    private Lock getLock = new ReentrantLock();
    private Condition getCondition = getLock.newCondition();

    /**
     * 插入，若队列已满则抛出异常
     *
     * @param e 待插入元素
     * @return 插入成功返回true
     * @throws IllegalStateException 如果队列已满
     */
    public boolean add(E e) {
        if (offer(e)) {
            return true;
        }
        throw new IllegalStateException("队列已满");
    }

    /**
     * 插入，若队列已满则等待
     *
     * @param e 待插入元素
     * @return 插入成功返回true
     * @throws InterruptedException 阻塞等待过程中被意外中断
     */
    public boolean put(E e) throws InterruptedException {
        putLock.lock();
        try {
            // 使用while的原因是，当该线程被唤醒后必须再次进行一次容量判断
            while (queue.size() == maxSize) {
                putCondition.await();
            }
            // 此时容量未满
            queue.add(e);
            getCondition.signalAll();
            return true;
        } finally {
            putLock.unlock();
        }
    }

    /**
     * 插入，若队列已满则返回特殊值
     *
     * @param e    待插入元素
     * @param time 等待时间
     * @param unit 时间单位
     * @return 插入成功返回true，插入失败返回false
     */
    public boolean offer(E e, long time, TimeUnit unit) throws InterruptedException {
        putLock.lock();
        try {
            while (queue.size() == maxSize) {
                if (!putCondition.await(time, unit)) {
                    // 超时退出
                    return false;
                }
            }
            // 此时容量未满
            queue.add(e);
            getCondition.signal(); // 插入一个元素，唤醒一个取除线程
            return true;
        } finally {
            putLock.unlock();
        }
    }

    /**
     * 插入，若队列已满则返回特殊值
     *
     * @param e 待插入元素
     * @return 插入成功返回true，插入失败返回false
     */
    public boolean offer(E e) {
        putLock.lock();
        try {
            if (queue.size() == maxSize) {
                return false;
            }
            // 此时容量未满
            queue.add(e);
            getCondition.signal(); // 插入一个元素，唤醒一个取除线程
            return true;
        } finally {
            putLock.unlock();
        }
    }

    /**
     * 移除，若队列为空返回{@code null}
     *
     * @return 等同于 {@link LinkedList#poll}
     */
    public E poll() {
        getLock.lock();
        try {
            if (queue.size() == 0) {
                return null;
            }
            return queue.removeFirst();
        } finally {
            getLock.unlock();
        }
    }

    /**
     * 移除，若队列为空返回{@code null}
     *
     * @param time 等待时间
     * @param unit 时间单位
     * @return 等同于 {@link LinkedList#poll}
     */
    public E poll(long time, TimeUnit unit) throws InterruptedException {
        getLock.lock();
        try {
            while (queue.size() == 0) {
                if (!getCondition.await(time, unit)) {
                    return null;
                }
            }
            return queue.removeFirst();
        } finally {
            getLock.unlock();
        }
    }

    /**
     * 移除元素，若队列为空抛异常
     *
     * @return 移除后的元素
     * @throws NoSuchElementException 为空则抛出
     */
    public E remove() {
        return Optional.ofNullable(poll()).orElseThrow(NoSuchElementException::new);
    }

    public E take() throws InterruptedException {
        getLock.lock();
        try {
            while (queue.size() == 0) {
                getCondition.await();
            }
            return queue.removeFirst(); // 理论上来说不会抛出NoSuchMethodException
        } finally {
            getLock.unlock();
        }
    }

    // TODO element() & peek()
}
