package com.example.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//定义资源类
class Tickets {
    private static int count = 250;

    /*
    //抢票
    public synchronized  void sale(){
        if(count > 0){
            System.out.println(Thread.currentThread().getName()+"买到了第"+(count--)+"张票，剩余"+count+"张票");
        }
    } */
    //Lock锁实现同步
    Lock lock = new ReentrantLock();
    public void sale(){
        lock.lock();//加锁
        if(count > 0){
            System.out.println(Thread.currentThread().getName()+"买到了第"+(count--)+"张票，剩余"+count+"张票");
        }
        lock.unlock();//解锁
    }
}

public class TestLock {

    public static void main(String[] args) {
        Tickets tickets = new Tickets();

        new Thread(()->{ for(int i=0; i<100; ++i) tickets.sale();}).start();
        new Thread(()->{ for(int i=0; i<100; ++i) tickets.sale();}).start();
        new Thread(()->{ for(int i=0; i<100; ++i) tickets.sale();}).start();
    }

}
