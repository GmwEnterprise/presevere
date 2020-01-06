package cn.gmwenterprise.thinkinjava.concurrency;

public class MainThread {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new LiftOff()).start();
        }
        System.out.println("Waiting for LiftOff...");
    }
}
