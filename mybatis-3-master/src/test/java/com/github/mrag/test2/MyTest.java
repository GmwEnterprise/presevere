package com.github.mrag.test2;

import org.junit.jupiter.api.Test;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class MyTest {
    @Test
    void f() throws Throwable {
        Type type1 = A.class.getGenericSuperclass();
        System.out.printf("A.class.getGenericSuperclass(): %s%n%n", type1.getClass().getSimpleName());

        for (Type interfaceType : A.class.getGenericInterfaces()) {
            System.out.printf("Super interface type: %s / %s\n\n",
                    interfaceType.getTypeName(), interfaceType.getClass().getSimpleName());
        }

        for (Field f : A.class.getDeclaredFields()) {
            System.out.printf("fieldName:      %s%n", f.getName());
            System.out.printf("fieldType:      %s%n", f.getGenericType().getTypeName());
            System.out.printf("fieldTypeClass: %s%n", f.getGenericType().getClass().getSimpleName());
            System.out.printf("fieldSuperclass: %s%n", f.getClass().getSuperclass().getSimpleName());
            if (f.getGenericType() instanceof GenericArrayType) {
                // 数组类型
                Type componentType = ((GenericArrayType) f.getGenericType()).getGenericComponentType();
                System.out.printf("fieldElementType: %s; fieldElementTypeClass: %s%n",
                        componentType.getTypeName(), componentType.getClass().getSimpleName());
            }
            System.out.println();
        }

        for (Method m : A.class.getDeclaredMethods()) {
            System.out.printf("methodName: %s%n", m.getName());
            System.out.printf("methodReturnType: %s%n", m.getGenericReturnType().getTypeName());
            System.out.printf("methodReturnTypeClass: %s%n", m.getGenericReturnType().getClass().getSimpleName());
            System.out.printf("methodParams: [%s]%n%n", Arrays.stream(m.getGenericParameterTypes())
                    .map(type -> type.getTypeName() + ":" + type.getClass().getSimpleName())
                    .collect(Collectors.joining(", ")));
        }
    }

    @Test
    void f2() throws Throwable {
        assert int.class == Integer.TYPE;

        Field type = B.class.getDeclaredField("type");
        System.out.println(type.getGenericType().getClass().getSimpleName());
        Field number = B.class.getDeclaredField("number");
        System.out.println(number.getGenericType().getClass().getSimpleName());

        for (Type actualTypeArgument : ((ParameterizedType) type.getGenericType()).getActualTypeArguments()) {
            System.out.println(actualTypeArgument);
            System.out.println(actualTypeArgument.getClass().getSimpleName());
            if (actualTypeArgument instanceof WildcardType) {
                System.out.print("上边界：");
                for (Type upperBound : ((WildcardType) actualTypeArgument).getUpperBounds()) {
                    System.out.print(upperBound + "  ");
                }
                System.out.println();
                System.out.print("下边界：");
                for (Type upperBound : ((WildcardType) actualTypeArgument).getLowerBounds()) {
                    System.out.print(upperBound + "  ");
                }
                System.out.println();
            }
        }

        for (Type actualTypeArgument : ((ParameterizedType) number.getGenericType()).getActualTypeArguments()) {
            System.out.println(actualTypeArgument);
            System.out.println(actualTypeArgument.getClass().getSimpleName());
            if (actualTypeArgument instanceof WildcardType) {
                System.out.print("上边界：");
                for (Type upperBound : ((WildcardType) actualTypeArgument).getUpperBounds()) {
                    System.out.print(upperBound + "  ");
                }
                System.out.println();
                System.out.print("下边界：");
                for (Type upperBound : ((WildcardType) actualTypeArgument).getLowerBounds()) {
                    System.out.print(upperBound + "  ");
                }
                System.out.println();
            }
        }
    }

    @Test
    void f3() throws Throwable {
    }
}

class A<T> extends ArrayList<String> implements Callable<T> {
    T typeVariable;
    int count;
    String name;
    String[] ids;
    int[] arr;
    Map<String, Integer> map;
    List<T> tList;
    T[] array;
    Callable<Integer>[] futures;
    T get(T origin) { return origin; }
    void func(T t, Map<T, String> aMap) { }
    @Override public T call() throws Exception { return null; }
}

class B {
    Callable<? extends Number> number;
    Class<?> type;
    List<? super Object> s;
}