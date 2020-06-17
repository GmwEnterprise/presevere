package com.github.mrag.mbsource;

import java.lang.reflect.Method;

public class App3 {
    public static void main(String[] args) {
        for (Method method : W.class.getMethods()) {
            String s = getSignature(method);
            if (s != null) {
                System.out.println(s);
            }
        }
    }

    public static String getSignature(Method method) {
        Class<?> declaringClass = method.getDeclaringClass();
        if (declaringClass.equals(Object.class)) {
            return null;
        }
        StringBuilder sb = new StringBuilder(declaringClass.getName()).append("->");
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

interface A {
    void aa();
    default void fuckA() {}
} interface B {} interface C {} interface D {}
class Q implements A, B {
    public String qq() {return "q";}

    @Override
    public void aa() {
        System.out.println("implements A");
    }
}
class W extends Q implements C, D {
    public void www() {}
}

interface E extends A {}

