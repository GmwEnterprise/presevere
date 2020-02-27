package cn.gmwenterprise.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SubsetsII {
}

class Solution {
//    public String convert(String s, int numRows) {
        // 1-1
        // 2-2
        // 3-4
        // 4-6
        // 5-8
        // 6-10

        // 1: 0, 2 * (numRows - 1)
        // 2: 2, 2 * (numRows - 2)
        // 3:
//    }

    public static int reverse(int x) {
        try {
            String[] strs = String.valueOf(Math.abs(x)).split("");
            Collections.reverse(Arrays.asList(strs));
            if (x < 0) {
                return Integer.parseInt("-" + String.join("", strs));
            }
            return Integer.parseInt(String.join("", strs));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void main(String[] args) {
        System.out.println(reverse(123));
        System.out.println(reverse(1000009999));
        System.out.println(reverse(-12314));
    }
}
