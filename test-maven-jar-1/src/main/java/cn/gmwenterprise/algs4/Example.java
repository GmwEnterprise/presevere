package cn.gmwenterprise.algs4;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Example {

    private static <T extends Comparable<T>> void sort(T[] a) {

    }

    /**
     * a是否小于b
     */
    private static <T extends Comparable<T>> boolean less(T a, T b) {
        return a.compareTo(b) < 0;
    }

    /**
     * == swap
     */
    private static <T extends Comparable<T>> void exch(T[] a, int i, int j) {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * output
     */
    private static <T extends Comparable<T>> void show(T[] a) {
        for (T t : a) {
            StdOut.print(t + " ");
        }
        StdOut.println();
    }

    /**
     * 是否有序
     */
    public static <T extends Comparable<T>> boolean isSorted(T[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
