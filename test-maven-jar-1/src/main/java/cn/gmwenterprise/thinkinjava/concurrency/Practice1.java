package cn.gmwenterprise.thinkinjava.concurrency;

public class Practice1 implements Runnable {
    private int count = 4;
    private static int taskCount = 0;
    private final int id = taskCount++;

    public Practice1() {
        System.out.printf("[%d]Start !%n", id);
    }

    @Override
    public void run() {
        while (count-- > 1) {
            System.out.printf("[%d]count: %d%n", id, count);
            Thread.yield();
        }
        System.out.printf("[%d]End !%n", id);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(new Practice1()).start();
        }
        System.out.println("----------------");
    }
}
