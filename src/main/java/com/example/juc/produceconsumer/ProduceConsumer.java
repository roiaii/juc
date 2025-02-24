package com.example.juc.produceconsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProduceConsumer {

    public List<Integer> list = new ArrayList<>();
    public int idx1 = 0, idx2 = 0, idx = 0;
    int[] nums1 = new int[]{1, 2, 3}, nums2 = new int[]{4, 5, 6};
    public static void main(String[] args) {
        ProduceConsumer produceConsumer = new ProduceConsumer();
        produceConsumer.runTask();
    }

    public void runTask() {
        new Thread(() -> {
            for (int i=0; i<nums1.length; i++) {
                produce(nums1, idx1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i=0; i<nums2.length; i++) {
                produce(nums2, idx2);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "B").start();

        new Thread(() -> {
            while (list.size() > 0) {
                consumer(list, 0);
            }
        }, "C").start();
    }



    private synchronized void produce(int[] nums, int idx) {
        while (list.size() > 0) {
            try {
                this.wait();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(nums[idx]);
        System.out.println(Thread.currentThread().getName() + ":" + nums[idx]);
        idx1++;
        this.notifyAll();
    }

    private synchronized void consumer(List<Integer> list, int idx) {
        while (list.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int a = list.get(idx);
        System.out.println(Thread.currentThread().getName() + ":" + a);
        list.remove(idx);
        this.notifyAll();
    }
}
