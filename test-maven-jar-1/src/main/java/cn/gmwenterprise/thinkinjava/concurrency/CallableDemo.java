package cn.gmwenterprise.thinkinjava.concurrency;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableDemo {

    public static void main(String[] args) {
        var exec = Executors.newCachedThreadPool();
        var results = new ArrayList<Future<String>>();
        for (int i = 0; i < 10; i++) {
            results.add(exec.submit(new TaskWithResult(i)));
        }
        for (Future<String> fs : results) {
            try {
                // get方法在完成前会阻塞
                System.out.println(fs.get());
            } catch (InterruptedException | ExecutionException e) {
                System.err.println(e);
                return;
            } finally {
                exec.shutdown();
            }
        }
    }
}

class TaskWithResult implements Callable<String> {
    private int id;

    TaskWithResult(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        System.out.printf("[%d] is called.%n", id);
        return String.format("result of TaskWithResult [%d]", id);
    }
}