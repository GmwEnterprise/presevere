package cn.gmwenterprise.thinkinjava.concurrency;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Practice24 {
    public static void main(String[] args) throws InterruptedException {
        var center = new Center();
        TimeUnit.SECONDS.sleep(15);
        center.exec.shutdownNow();
    }
}

class Center {
    final Producer producer = new Producer(this);
    final Consumer consumer = new Consumer(this);
    ExecutorService exec = Executors.newCachedThreadPool();

    Center() {
        exec.execute(producer);
        exec.execute(consumer);
    }
}

class Product {
    public static List<Product> products = Collections.synchronizedList(new LinkedList<>());
    public static final int maxCapacity = 20;

    private final int orderNum;

    public Product(int orderNum) { this.orderNum = orderNum; }

    @Override
    public String toString() { return String.format("Product[%d]. ", orderNum); }
}

class Producer implements Runnable {
    private int count = 0;
    private Center center;

    public Producer(Center c) { center = c; }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (this) {
                    while (Product.products.size() >= Product.maxCapacity) {
                        wait();
                    }
                }
                synchronized (center.consumer) {
                    var newProduct = new Product(++count);
                    System.out.print(newProduct);
                    Product.products.add(newProduct);
                    center.consumer.notifyAll();
                }
                TimeUnit.MILLISECONDS.sleep(400);
            }
        } catch (InterruptedException e) {
            System.out.println("Producer interrupted");
        }
    }
}

class Consumer implements Runnable {
    private Center center;

    public Consumer(Center c) { center = c; }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (this) {
                    while (Product.products.size() < 1) {
                        wait();
                    }
                    synchronized (center.producer) {
                        System.out.printf("%nuse one: %s, rest size: %d%n",
                            Product.products.remove(0),
                            Product.products.size());
                        center.producer.notifyAll();
                    }
                }
                TimeUnit.MILLISECONDS.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Consumer interrupted");
        }
    }
}