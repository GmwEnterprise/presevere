package com.github.mrag.mbsource;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class App4 {
    public static void main(String[] args) throws Throwable {
        Class<? extends ArrayList> aClass = new ArrayList<String>().getClass();
        Method elementData = aClass.getDeclaredMethod("elementData", int.class);
        System.out.println(elementData.getGenericReturnType());
        System.out.println(elementData.getReturnType());
    }
}
