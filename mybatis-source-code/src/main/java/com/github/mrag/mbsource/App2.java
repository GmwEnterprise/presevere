package com.github.mrag.mbsource;

import java.lang.reflect.Method;

public class App2 {
    public static void main(String[] args) throws Throwable {
        for (Method method : Object.class.getDeclaredMethods()) {
            System.out.println(getSignature(method));
        }
    }

    public static String getSignature(Method method) {
        StringBuilder sb = new StringBuilder();
        Class<?> returnType = method.getReturnType();
        if (returnType != null) {
            sb.append(returnType.getName()).append('#');
        }
        sb.append(method.getName());
        Class<?>[] parameters = method.getParameterTypes();
        for (int i = 0; i < parameters.length; i++) {
            sb.append(i == 0 ? ':' : ',').append(parameters[i].getName());
        }
        return sb.toString();
    }
}
