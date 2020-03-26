package cn.gmwenterprise.test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class Concurrent1 {
    private static AtomicLong counter = new AtomicLong();

    public static void main(String[] args) {
        ExecutorService exec = new ThreadPoolExecutor(
            0, Integer.MAX_VALUE, 0L, TimeUnit.SECONDS, new SynchronousQueue<>(),
            task -> new Thread(task, String.format("Thread[%d]", counter.incrementAndGet()))) {

            @Override
            public void execute(Runnable command) {
                super.execute(wrap(command, clientTrace()));
            }

            @Override
            public Future<?> submit(Runnable task) {
                return super.submit(wrap(task, clientTrace()));
            }

            private Exception clientTrace() {
                /*
                 * 这个异常会在线程开始运行的时候就被创建，其中就包括了属于这个线程的栈信息；
                 * 在遇到异常的时候捕获异常，并打印这个拥有栈信息的异常
                 */
                return new Exception("Client stack trace");
            }

            private Runnable wrap(Runnable task, Exception clientTask) {
                return () -> {
                    try {
                        task.run();
                    } catch (Exception e) {
                        clientTask.printStackTrace();
                        e.printStackTrace();
                        throw e;
                    }
                };
            }

        };
        for (int i = 0; i < 5; i++) {
            exec.submit(new DivTask(100, i));
        }
        exec.shutdown();
    }
}

class DivTask implements Runnable {
    int a, b;

    public DivTask(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        double re = a / b;
        System.out.println(re);
    }
}