package cn.gmwenterprise.java_detail;

public class Test2 {
    public static void main(String[] args) {
        Child c = new Child();
        c.action();

        Base b = c;
        b.action();
    }
}


class Base {
    public static int s;
    private int a;

    static {
        System.out.println("基类静态代码块，s: " + s);
        s = 1;
    }

    {
        System.out.println("基类实例代码块，a: " + a);
        a = 1;
    }

    public Base() {
        System.out.println("基类构造方法，a: " + a);
        a = 2;
    }

    protected void step() {
        System.out.println("base s: " + s + ", a = " + a);
    }

    public void action() {
        System.out.println("start");
        step();
        System.out.println("end");
    }
}

class Child extends Base {
    public static int s;
    private int a;

    static {
        System.out.println("子类静态代码块，s: " + s);
        s = 10;
    }

    {
        System.out.println("子类实例代码块，a: " + a);
        a = 10;
    }

    public Child() {
        System.out.println("子类构造方法，a: " + a);
        a = 20;
    }

    @Override
    protected void step() {
        System.out.println("base s: " + s + ", a = " + a);
    }
}