package com.LRU;

import java.util.HashMap;

//再写一遍
//LRU最近最少使用，基于使用次序进行淘汰

class ListNode{
    int key;
    int val;
    ListNode next;
    ListNode pre;
    public ListNode(){}
}

//再是写一遍
public class MyLRU {
    private HashMap<Integer, ListNode> h;
    private ListNode head = null;
    private ListNode tail = null;
    private int capacity = 0;
    private int size = 0;

    public MyLRU(int capacity){
        this.capacity = capacity;
        h = new HashMap<>();
        head = new ListNode();
        tail = new ListNode();
        head.next = tail;
        tail.pre = head;
    }

    private void insertHead(ListNode h, ListNode t){
        t.next.pre = t.pre;
        t.pre.next = t.next;
        t.next = h.next;
        h.next.pre = t;
        t.pre = h;
        h.next = t;
    }


    public int get(int key){
        if(!h.containsKey(key)) return -1; //如果缓存未命中，那应该从磁盘加载到缓存，并放在链表头
        else{
            ListNode t = h.get(key);
            insertHead(head, t);
            return t.val;
        }
    }


    public void put(int key, int value){
        if(h.containsKey(key)){
            ListNode t = h.get(key);
            t.val = value;
            insertHead(head, t);
        }else{
            ListNode t = new ListNode();
            t.key = key;
            t.val = value;
            h.put(key, t);
            t.next = head.next;
            head.next.pre = t;
            t.pre = head;
            head.next = t;
            size++;

            //如果超过缓存区大小，则进行内存替换策略
            if(size > capacity){
                //按照最久未使用，换出链表尾结点
                ListNode p = tail.pre;
                p.next.pre = p.pre;
                p.pre.next = p.next;
                h.remove(p.key);
                size++;
            }
        }
    }




}






/*
public class MyLRU{
    private int size; //缓存大小
    private int capacity;
    private ListNode head = null;
    private ListNode tail = null;
    private HashMap<Integer, ListNode> h = null;

    public MyLRU(int capacity){
        size = 0;
        this.capacity = capacity;
        head = new ListNode();
        tail = new ListNode();
        head.next = tail;
        tail.pre = head;
        h = new HashMap<>();
    }

    public int get(int key) {  //get操作不用考虑替换处理
        if(!h.containsKey(key)) return -1; //缓存中没有，返回-1
        ListNode temp = h.get(key);
        temp.pre.next = temp.next;
        temp.next.pre = temp.pre;

        temp.next = head.next;
        head.next.pre = temp;
        temp.pre = head;
        head.next = temp;

        return temp.val;
    }

    public void put(int key, int val){
        if(h.containsKey(key)){
            this.get(key);
        } else{
            ListNode temp = new ListNode();
            temp.key = key;
            temp.val = val;
            h.put(key, temp);

            temp.next = head.next;
            head.next.pre = temp;
            temp.pre = head;
            head.next = temp;

            this.size++;

            while(this.size > this.capacity){
                //需要执行替换处理
                ListNode t = tail.pre;
                h.remove(t.key);
                tail.pre = t.pre;
                t.pre.next = tail;
                size--;
            }
        }
    }
} */

class TestLRU{
    public static void main(String[] args) {
        MyLRU myLRU = new MyLRU(3);

        System.out.println(myLRU.get(1));

        myLRU.put(1,11);
        myLRU.put(2,12);
        myLRU.put(3,13);

        System.out.println(myLRU.get(1));

        myLRU.put(4,14);

        System.out.println(myLRU.get(2));  //返回-1，说明被替换掉了

    }
}





/*
双向链表+哈希表实现
 */
/*
class ListNode {
    int val;
    int key;
    ListNode next;
    ListNode pre;
    public ListNode(){}
}
public class MyLRU {
    HashMap<Integer, ListNode> h;
    int capacity;
    int size;
    ListNode head, tail;

    public  MyLRU(int capacity) {
        this.capacity = capacity;
        size = 0;
        head = new ListNode();
        tail = new ListNode();
        head.next = tail;
        tail.pre = head;
        h = new HashMap<>();
    }

    //get()
    public int get(int key) {
        if(!h.containsKey(key)) return -1;

        ListNode temp = h.get(key);
        temp.pre.next = temp.next;
        temp.next.pre = temp.pre;

        temp.next = head.next;
        head.next.pre = temp;
        temp.pre = head;
        head.next = temp;

        return temp.val;
    }

    //put()
    public void put(int key, int value) {
        if(h.containsKey(key)) {
            ListNode temp = h.get(key);
            temp.val = value;
            h.put(key,  temp);

            temp.pre.next = temp.next;
            temp.next.pre = temp.pre;

            temp.next = head.next;
            head.next.pre = temp;
            temp.pre = head;
            head.next = temp;

        } else {
            ListNode temp = new ListNode();
            temp.key = key;
            temp.val = value;
            h.put(key, temp);

            temp.next = head.next;
            head.next.pre = temp;
            temp.pre = head;
            head.next = temp;

            size++;

            //替换
            if(size > capacity) {
                ListNode t = tail.pre;
                h.remove(t.key);

                t.pre.next = tail;
                tail.pre = t.pre;
                size--;
            }
        }
    }
}

//主函数
class Solution {
    public static void main(String[] args) {
        MyLRU myLRU = new MyLRU(3);

        //处理输入


        //test
        System.out.println(myLRU.get(0)); //-1

        myLRU.put(0, 10);
        myLRU.put(1, 11);
        myLRU.put(2, 12);

        System.out.println(myLRU.get(0));  //10

        myLRU.put(3, 13);

        System.out.println(myLRU.get(1));  //-1

    }
} */
