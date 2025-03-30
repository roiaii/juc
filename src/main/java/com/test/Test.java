package com.test;

import org.springframework.context.annotation.Conditional;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        n = 2133;
        int[] nums = new int[]{1, 2, 3, 9};
        used = new boolean[nums.length];
        len = 4;
        dfs(nums);
        System.out.println(ans);
    }

    static int ans = 0;
    static int len = 0;
    static int n = 0;
    static boolean[] used;
    static ArrayList<Integer> path = new ArrayList<>();


    /**
     * 从nums数组进行元素组合，可以从重复选，寻找不大于n的最大的数
     * @param nums
     */
    private static void dfs(int[] nums) {
        if (path.size() > len) {
            return;
        }

        int t = calNum(path);

        if (t > ans && t < n) {
            ans = t;
        }

        for (int i=0; i<nums.length; i++) {
            path.add(nums[i]);
            dfs(nums);
            path.remove(path.size()-1);
        }
    }

    private static int calNum(ArrayList<Integer> lists) {
        int ans = 0;
        int ff = 0;

        for (int i= lists.size()-1; i>=0; i--) {
            ans += (lists.get(i) * Math.pow(10, ff++));
        }

        return ans;

    }
}

