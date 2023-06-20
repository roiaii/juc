package com.example.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *使用Condition实现精准通知唤醒
 */

/**
 * 实现按照ABCA的线程顺序执行，用Condition精准唤醒实现
 */
public class PcLock2 {
    public static void main(String[] args) {
        //创建资源类
        Data3 data3 = new Data3();
        new Thread(()->{for(int i=0; i<20; ++i) data3.A();}).start();
        new Thread(()->{for(int i=0; i<20; ++i) data3.B();}).start();
        new Thread(()->{for(int i=0; i<20; ++i) data3.C();}).start();
    }
}

//资源类
class Data3{
    private int count = 1;

    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    //A
    public void A(){
        lock.lock();
        try {
            while(count != 1){
                condition1.await();
            }
            count = 2;
            System.out.println(Thread.currentThread().getName()+"->AAAA");
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //B
    public void B(){
        lock.lock();
        try {
            while(count != 2) {
                condition2.await();
            }
            count = 3;
            System.out.println(Thread.currentThread().getName()+"->BBBB");
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //C
    public void C(){
        lock.lock();
        try {
            while(count != 3) {
                condition3.await();
            }
            count = 1;
            System.out.println(Thread.currentThread().getName()+"->CCCC");
            condition1.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

