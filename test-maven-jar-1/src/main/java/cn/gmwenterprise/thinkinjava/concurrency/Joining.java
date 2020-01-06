package cn.gmwenterprise.thinkinjava.concurrency;

public class Joining {
    public static void main(String[] args) {
        var sleepy = new Sleeper("Sleepy", 1500);
        var grumpy = new Sleeper("Grumpy", 1500);

        var dopey = new Joiner("Dopey", sleepy);
        var doc = new Joiner("Doc", grumpy);

        /*
        主动调用Thread.interrupt()时，JVM会给目标线程设置中断标志:
            isInterrupted() = true
            此标志表示该线程被中断
        但是在目标线程内部调用该方法时，这个标志又会被清理掉
         */
        grumpy.interrupt();
    }
}

class Sleeper extends Thread {
    private int duration;

    public Sleeper(String name, int sleepTime) {
        super(name);
        this.duration = sleepTime;
        start();
    }

    @Override
    public void run() {
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            System.out.println(getName() + " was interrupted. isInterrupted(): " + isInterrupted());
            return;
        }
        System.out.println(getName() + " has awakened.");
    }
}

class Joiner extends Thread {
    private Sleeper sleeper;

    public Joiner(String name, Sleeper sleeper) {
        super(name);
        this.sleeper = sleeper;
        start();
    }

    @Override
    public void run() {
        try {
            sleeper.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted.");
        }
        System.out.println(getName() + " join completed.");
    }
}