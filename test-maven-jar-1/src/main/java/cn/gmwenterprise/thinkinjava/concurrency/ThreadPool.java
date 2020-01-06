package cn.gmwenterprise.thinkinjava.concurrency;

import java.util.concurrent.*;

public class ThreadPool {

    public void fixed() {
        // Executors.newFixedThreadPool(5);
        var exec = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }

    public void cached() {
        // Executors.newCachedThreadPool();
        var exec = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
        for (int i = 0; i < 5; i++) {
            exec.execute(new LiftOff());
        }
        exec.shutdown();
    }
}
