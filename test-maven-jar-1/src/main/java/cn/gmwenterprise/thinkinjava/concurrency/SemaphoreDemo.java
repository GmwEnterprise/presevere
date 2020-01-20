package cn.gmwenterprise.thinkinjava.concurrency;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class CheckoutTask<T> implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private Pool<T> pool;

    public CheckoutTask(Pool<T> pool) { this.pool = pool; }

    @Override
    public void run() {
        try {
            var item = pool.checkOut();
            System.out.printf("%schecked out %s%n", this, item);
            TimeUnit.SECONDS.sleep(1);
            System.out.printf("%schecking in %s%n", this, item);
            pool.checkIn(item);
        } catch (InterruptedException ignored) {}
    }

    @Override
    public String toString() { return String.format("CheckoutTask %d ", id); }
}

public class SemaphoreDemo {
    static final int SIZE = 25;

    public static void main(String[] args) throws InterruptedException {
        var pool = new Pool<>(Fat.class, SIZE);
        var exec = Executors.newCachedThreadPool();
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new CheckoutTask<>(pool));
        }
        System.out.println("All CheckoutTasks created");
        var list = new ArrayList<Fat>();
        for (int i = 0; i < SIZE; i++) {
            var f = pool.checkOut();
            System.out.print(i + ": main() thread checked out ");
            f.operation();
            list.add(f);
        }
        Future<?> blocked = exec.submit(() -> {
            try {
                pool.checkOut();
            } catch (InterruptedException e) {
                System.out.println("checkOut() interrupted");
            }
        });
        TimeUnit.SECONDS.sleep(2);
        blocked.cancel(true);
        System.out.println("Checking in objects in " + list);
        for (Fat f : list) {
            pool.checkIn(f);
        }
        for (Fat f : list) {
            pool.checkIn(f);
        }
        exec.shutdown();
    }
}
