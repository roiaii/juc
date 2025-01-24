package com.test;

import java.util.Scanner;




class ListNode {
    int val;
    ListNode next;
    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
public class Test {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ListNode h = new ListNode(0, null);
        int n = sc.nextInt();
        for (int i=0; i<n; i++) {
            int e = sc.nextInt();
            System.out.println("元素：" + e);
            ListNode l = new ListNode(e, null);
            // 头插法
            l.next = h.next;
            h.next = l;
        }


        boolean ans = judge(h);

        System.out.println(ans);

    }

    // 判断是否回文链表
    public static boolean judge (ListNode h) {
        if (h == null) {
            return false;
        }

        int len = 0;
        ListNode cur = h;
        while (cur.next != null) {
            len++;
            cur = cur.next;
        }

        int s = len / 2;
        cur = h;
        while (s >= 0) {
            s--;
            cur = cur.next;
        }


        ListNode reverseList = null;
        if (len % 2 == 0) {
            reverseList = reverseList(cur);
        } else {
            reverseList = reverseList(cur.next);
        }



        ListNode p1 = h.next, p2 = reverseList;

        while (p1 != null && p2 != null) {
            if (p1.val != p2.val) {
                return false;
            }
            p1 = p1.next;
            p2 = p2.next;
        }
        return true;
    }

    // 反转链表
    public static ListNode reverseList (ListNode h) {
        if (h == null || h.next == null) {
            return h;
        }
        ListNode last = reverseList(h.next);
        h.next.next = h;
        h.next = null;
        return last;
    }
}