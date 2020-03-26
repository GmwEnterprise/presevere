package cn.gmwenterprise.test;

import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTaskDemo {
    public static void main(String[] args) {

    }
}

class CountTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10000;
    private long start;
    private long end;

    CountTask(long s, long e) {
        start = s;
        end = e;
    }

    @Override
    public Long compute() {
        long sum = 0;
        boolean canCompute = (end - start) < THRESHOLD;
        if (canCompute) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 分成100个小任务
            long step = (start + end) / 100;
            ArrayList<CountTask> subTasks = new ArrayList<>();
            long pos = start;
            for (int i = 0; i < 100; i++) {
                long lastOne = pos + step;
                if (lastOne > end) {
                    lastOne = end;
                }
                CountTask subTask = new CountTask(pos, lastOne);
                pos += step + 1;
                subTasks.add(subTask);
                subTask.fork();
            }
            for (CountTask t : subTasks) {
                sum += t.join();
            }
        }
        return sum;
    }
}