package com.test;

import org.springframework.context.annotation.Conditional;

import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

    public static void main(String[] args) {
        Data data = new Data();
        for (int i=0; i<10; i++) {
            new Thread(() -> {
                data.product();
            }).start();
        }

        CompletableFuture cf = new CompletableFuture();

        new Thread(() -> {
            for (int i=0; i<10; i++) {
                data.consumer();
            }
        }).start();
    }
}

class Data {
    int count = 0;

    Lock lock = new ReentrantLock();

    Condition condition1 = lock.newCondition();

    Condition condition2 = lock.newCondition();

    public void product() {
        lock.lock();
        try {
            while (count > 0) {
                // this.wait();
                condition1.await();
            }
            // 生产消息
            count++;
            System.out.println(Thread.currentThread().getName() + "生产了" + count);
            // 唤醒消费者
            // this.notifyAll();
            condition2.signal();
        } catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }
    }

    public void consumer() {
        lock.lock();
        try {
            while (count == 0) {
                // this.wait();
                condition2.await();
            }
            // 消费
            System.out.println(Thread.currentThread().getName() + "消费了" + count);
            count--;
            // this.notifyAll();
            condition1.signal();
        } catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }
    }
}
