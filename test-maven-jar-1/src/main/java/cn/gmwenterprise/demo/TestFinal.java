package cn.gmwenterprise.demo;

public class TestFinal {
    public static void main(String[] args) {
        var obj = new FinalObj();
        obj.o.value = 100;
        System.out.println(obj.o.value);
//        obj.o = null;
    }
}

class FinalObj {
    final Obj o = new Obj();
}

class Obj {
    int value;
}

