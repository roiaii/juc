package com.example.juc.productCostum;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynPc {
    public static void main(String[] args) {
        //test
        Data data = new Data();

        new Thread(() ->{
            for(int i=0; i<10; i++)
                data.product();},"productA").start();
        new Thread(() -> {
            for(int i=0; i<10; i++)
                data.customer();
        }, "customerA").start();

        new Thread(() -> {
            for(int i=0; i<10; i++)
                data.customer();
        }, "customerB").start();

    }
}



/*
共享资源类
在类里面提供同步的生产者消费者方法
 */
class Data {
    private int counter = 10;

/*
    //Synchronized实现同步
    //生产者
    public synchronized void product() {
        while(counter > 0) {
            try{
                this.wait(); //缓冲区有资源，阻塞
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        counter++;
        System.out.println(Thread.currentThread().getName() + counter);
        this.notifyAll(); //执行完毕后唤醒其他等待线程
    }

    //消费者
    public synchronized void customer(){
        while(counter == 0) {
            try{
                this.wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        counter--;
        System.out.println(Thread.currentThread().getName() + counter);
        this.notifyAll();
    } */

    //使用Lock来实现同步

    private Lock lock = new ReentrantLock();

    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();

    //生产者
    public void product(){
        lock.lock();
        try{
            while(counter > 0) {
                    condition1.await();
                }
            counter++;
            condition2.signalAll();  //为什么用Condition2，因为你要唤醒用Condition2阻塞住的线程
            System.out.println(Thread.currentThread().getName() + counter);

        } catch(Exception e) {
        e.printStackTrace();
    } finally {
        lock.unlock();
    }


    }

    //消费者
    public void customer(){
        lock.lock();
        try{
            while(counter == 0) {
                    condition2.await();  //这个方法就要用Condition2阻塞住，区别开Condition1阻塞线程
                }
            counter--;
            condition1.signalAll();
            System.out.println(Thread.currentThread().getName() + counter);
        } catch(Exception e) {
        e.printStackTrace();
    } finally {
            lock.unlock();
        }
    }
}
