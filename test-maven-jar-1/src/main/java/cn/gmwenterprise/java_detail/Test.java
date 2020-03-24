package cn.gmwenterprise.java_detail;

public class Test {
    public static void main(String[] args) {
        new Circle().draw();
        new Shape().draw();
        System.out.println("=============");
        Shape circle = new Circle();
        circle.draw(); // output: `circle`
    }
}

class Shape {
    public void draw() {
        System.out.println("shape");
    }
}

class Circle extends Shape {
    @Override
    public void draw() {
        System.out.println("circle");
    }
}