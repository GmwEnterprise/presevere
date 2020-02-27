package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test1 {

    public static List<List<Integer>> subsets(int... nums) {
        List<List<Integer>> res = new ArrayList<>();


        for (int num : nums) {
            List<List<Integer>> resItem = new ArrayList<>();
            for (List<Integer> r : res) {
                List<Integer> item = new ArrayList<>(r);
                item.add(num);
                resItem.add(item);
            }
            List<Integer> e = new ArrayList<>();
            e.add(num);
            resItem.add(e);
            res.addAll(resItem);
        }

        res.add(Collections.emptyList());
        return res;
    }

    public static void main(String[] args) {
        System.out.println(subsets(1, 2, 3, 4));
    }
}
