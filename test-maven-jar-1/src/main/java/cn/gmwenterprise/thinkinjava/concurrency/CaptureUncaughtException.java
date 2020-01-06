package cn.gmwenterprise.thinkinjava.concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 解释线程如何处理异常
 */
public class CaptureUncaughtException {
    public static void main(String[] args) {
        var exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
        exec.execute(new ExceptionThread2());
        exec.shutdown();
    }
}

class ExceptionThread2 implements Runnable {
    @Override
    public void run() {
        var t = Thread.currentThread();
        System.out.println("run() by " + t);
        System.out.println("eh = " + t.getUncaughtExceptionHandler());
        throw new RuntimeException();
    }
}

class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static int idSequence = 1;
    private final int id = idSequence++;

    @Override
    public String toString() {
        return String.format("MyUncaughtExceptionHandler[%d]", id);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("caught: " + e);
    }
}

class HandlerThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        System.out.println(this + " creating new Thread");
        var t = new Thread(r);
        System.out.println("created " + t);
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        System.out.println("eh = " + t.getUncaughtExceptionHandler());
        return t;
    }
}