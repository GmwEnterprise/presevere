package com.github.mrag;

import org.apache.ibatis.reflection.Reflector;
import org.junit.jupiter.api.Test;

class People {
  private int sex; // 性别
  private String color; // 色种

  public int getSex() {
    return sex;
  }

  public void setSex(int sex) {
    this.sex = sex;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }
}

class Chinese extends People {
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

class TypeParameterResolverTest {

  @Test
  void testResolverMethod() {
    Reflector reflector = new Reflector(Chinese.class);
    assert reflector.hasGetter("sex");
  }
}
