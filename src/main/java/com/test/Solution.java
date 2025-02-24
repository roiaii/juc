package com.test;

import java.util.Arrays;
import java.util.HashMap;

public class Solution {
    public static void main(String[] args) {
        // case : "abcddfggh"   4
        System.out.println("pwwkew：" + getSubStringLength("pwwkew"));
    }

    /**
     * 最长无重复子串的长度
     */
    /**
     * 双指针实现
     * @param str
     * @return
     */
    /**
     * 示例 1:
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     * 输入: s = "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     * 输入: s = "pwwkew"
     * 输出: 3
     * @param str
     * @return
     */
    private static int getSubStringLength(String str) {
        HashMap<Character, Integer> h = new HashMap<>();
        int n = str.length();
        int ans = 0;

        // i 窗口扩大
        for (int i=0,j=0; i<n; i++) {
            char t = str.charAt(i);
            h.put(t, h.getOrDefault(t,0)+1);
            // j 窗口收缩
            while (i<n && j<i && h.get(t)>1) {
                char c = str.charAt(j);
                h.put(c, h.get(c)-1);
                j++;
            }
            // 结果收集
            int len = i - j + 1;
            ans = len > ans ? len : ans;
        }


        return ans;
    }
}
