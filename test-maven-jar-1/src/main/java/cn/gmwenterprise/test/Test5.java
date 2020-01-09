package cn.gmwenterprise.test;

interface P1 {
    void run();
}

interface P2 {
    void run();
}

class P3 {
    public void run() {
        System.out.println("rnm");
    }
}


class C1 extends P3 implements P1, P2 {

    @Override
    public void run() {
        System.out.println("fuck");
    }
}

public class Test5 {
    public static void main(String[] args) {
        new C1().run();
    }
}
