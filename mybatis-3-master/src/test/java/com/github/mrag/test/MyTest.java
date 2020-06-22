package com.github.mrag.test;

import org.apache.ibatis.reflection.TypeParameterResolver;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

class MyTest {

    @Test
    void test1() throws NoSuchMethodException {
        Type type = TypeParameterResolver
                .resolveReturnType(String.class.getDeclaredMethod("intern"), String.class);
        System.out.println(type);
    }

    @Test
    void test2() throws Throwable {

        Type type = Aclass.class.getDeclaredField("list").getGenericType();
        System.out.println(type instanceof ParameterizedType);
        System.out.println(type.getTypeName());
        System.out.println(type.getClass());

        System.out.println(type instanceof ParameterizedType);
        System.out.println(type.getTypeName());
    }
}

class Aclass {
    private List<String> list;
}

class BClass extends ArrayList<String> {
}