package cn.gmwenterprise.test;

import java.util.Arrays;

public class Test1 {
    //  a  a  a  a  a  b  b  c  c  c   =>   a  5  b  2  c  3
    //  0  1  2  3  4  5  6  7  8  9   =>   0  1  2  3  4  5
    //  anchor
    //                 read
    public static int compress(char[] chars) {
        int anchor = 0, write = 0;
        for (int read = 0; read < chars.length; read++) {
            if (read + 1 == chars.length || chars[read + 1] != chars[read]) {
                chars[write++] = chars[anchor];
                if (read > anchor) {
                    for (char c: ("" + (read - anchor + 1)).toCharArray()) {
                        chars[write++] = c;
                    }
                }
                anchor = read + 1;
            }
        }
        return write;
    }

    public static void main(String[] args) {
        char[] arr = "aaaaaabbbccccc".toCharArray();
        System.out.println(Arrays.toString(arr));
        System.out.println(compress(arr));
        System.out.println(Arrays.toString(arr));
    }
}
