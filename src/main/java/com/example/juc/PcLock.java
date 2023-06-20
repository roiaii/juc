package com.example.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
使用Lock实现生产者消费者机制
使用Condition接口配合 实现等待通知模式
 */
//再写一遍，用lock和Condition实现生产者消费者
public class PcLock {
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(()->{
            for(int i=0; i<10; i++) {
                data.produce();
            }
        }, "A").start();
        new Thread(()->{
            for(int i=0; i<10; i++) {
                data.customer();
            }
        }, "B").start();
    }
    static class Data {
        private int count = 10;
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        public void produce() {
            lock.lock();
            try {
                while (count != 0) {
                    condition.await();
                }
                count++;
                System.out.println(Thread.currentThread().getName() + count);
                condition.signal();
            } catch(Exception e){
                    e.printStackTrace();
            } finally {
                    lock.unlock();
            }
        }

        public void customer() {
            lock.lock();
            try {
                while(count == 0){
                    condition.await();
                }
                count--;
                System.out.println(Thread.currentThread().getName() + count);
                condition.signal();
            } catch(Exception e){
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}


/*
public class PcLock {
    public static void main(String[] args) {
        //创建资源类
        Data2 data2 = new Data2();
        new Thread(()->{for(int i=0; i<20; ++i) data2.increase();}).start();
        new Thread(()->{for(int i=0; i<20; ++i) data2.increase();}).start();
        new Thread(()->{for(int i=0; i<20; ++i) data2.descend();}).start();
        new Thread(()->{for(int i=0; i<20; ++i) data2.descend();}).start();
    }
}

//资源类
class Data2{
    private int count = 0;

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    //生产者
    public void increase(){
        lock.lock();
        try {
            while(count != 0){
                condition.await();
            }
            count++;
            System.out.println(Thread.currentThread().getName()+"->"+count);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //消费者
    public void descend(){
        lock.lock();
        try {
            while(count == 0) {
                condition.await();
            }
            count--;
            System.out.println(Thread.currentThread().getName()+"->"+count);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
*/