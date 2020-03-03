package cn.gmwenterprise.alg;

import java.util.Arrays;

/**
 * 排序 查找
 */
public final class SortAndSearch {

    // 查找

    /**
     * 二分法查找
     *
     * @param src   源数组
     * @param value 目标值
     * @return 目标值下标，若未找到则返回-1
     */
    public static int dichotomy(int[] src, int value) {
        int begin = 0, end = src.length - 1;
        quickSort(src, begin, end);
        return -1;
    }

    // 排序

    /**
     * 快速排序
     *
     * @param src   源数组
     * @param begin 起始索引
     * @param end   结束索引
     */
    public static void quickSort(int[] src, int begin, int end) {
        if (begin < end) {
            int i = begin, j = end, benchmark = src[begin];
            while (i < j) {
                while (i < j && src[j] > benchmark) {
                    j--;
                }
                if (i < j) {
                    // 高位替换低位
                    src[i++] = src[j];
                }
                while (i < j && src[i] < benchmark) {
                    i++;
                }
                if (i < j) {
                    // 低位替换高位
                    src[j--] = src[i];
                }
            }
            src[i] = benchmark;
            // 此时基准值为array[i]，且i == j
            System.out.println(Arrays.toString(src));
            quickSort(src, begin, i - 1);
            quickSort(src, i + 1, end);
        }
    }

    /*
    a = 5, b = 12

    a = 0111
    b = 1100

    a = a ^ b = 0111 ^ 1100 = 1011
    b = b ^ a = 1100 ^ 1011 = 0111
    a = a ^ b = 1011 ^ 0111 = 1100
     */

    /**
     * 选择排序
     *
     * @param src 源数组
     */
    public static void selectionSort(int[] src) {
        for (int i = 0; i < src.length; i++) {
            int min = i;
            for (int j = i; j < src.length; j++) {
                if (src[j] < src[min]) {
                    min = j;
                }
            }
            swap(src, i, min);
        }
        System.out.println(Arrays.toString(src));
    }

    /**
     * 堆排序
     *
     * @param src 源数组
     */
    public static void heapsort(int[] src) {

    }

    private static void swap(int[] src, int a, int b) {
        // 位运算交换整型值，二者不可为同一个地址，否则该地址值将为0
        if (a != b) {
            src[a] ^= src[b];
            src[b] ^= src[a];
            src[a] ^= src[b];
        }
    }

    public static void main(String[] args) {
        var array = new int[]{49, 38, 65, 97, 76, 13, 27, 49};
        selectionSort(array);
    }
}
