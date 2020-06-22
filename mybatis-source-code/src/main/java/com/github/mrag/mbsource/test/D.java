package com.github.mrag.mbsource.test;

class C {
    CharSequence get(CharSequence origin) {
        return origin.getClass().getName();
    }
}

public class D extends C {

    String get(String origin) {
        return origin.getClass().getName();
    }

    public static void main(String[] args) {
        System.out.println(new C().get("hello"));
    }
}
