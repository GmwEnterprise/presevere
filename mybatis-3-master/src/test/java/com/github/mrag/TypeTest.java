package com.github.mrag;

import org.apache.ibatis.reflection.TypeParameterResolver;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

class Shape<T> {
  private T id;
}

class Circle<E> extends Shape<E> {
}

class Circle2 extends Shape<String> {
}

class TypeTest {
  @Test
  void testType() throws Throwable {
    Field id = Circle2.class.getSuperclass().getDeclaredField("id");
    System.out.println(TypeParameterResolver.resolveFieldType(id, Circle2.class));
  }
}
