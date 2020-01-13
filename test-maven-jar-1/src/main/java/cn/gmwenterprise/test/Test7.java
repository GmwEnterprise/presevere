package cn.gmwenterprise.test;

public class Test7 {
    static synchronized void f() throws InterruptedException {
        Test7.class.wait();
    }

    public static void main(String[] args) throws InterruptedException {
        f();
    }
}
