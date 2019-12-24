package cn.gmwenterprise.test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TestIterator {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("A", "B", "C", "D", "E");
        list.removeIf("C"::equals);
        System.out.println(list);
    }
}
