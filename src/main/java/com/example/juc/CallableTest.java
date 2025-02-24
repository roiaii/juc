package com.example.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

//Callable实现多线程
/**
 * 1、Callable有返回值
 * 2、可以抛出异常
 * 3、线程执行体为call()方法
 * 4、使用适配类FutureTask来适配Thread进而启动线程
 */
public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //new Thread().start();
        //new FutureTask<>();//就相当于Runnable   //适配类
        /**
         * class FutureTask<V> implements RunnableFuture<V>
         * interface RunnableFuture<V> extends Runnable
         */
        //new MyThread();//实现callable接口的线程执行体
        new Thread(new FutureTask<>(new MyThread()), "A").start();
        new Thread(new FutureTask<>(new MyThread()), "B").start();
       /* MyThread myThread = new MyThread();
        FutureTask futureTask = new FutureTask(myThread);
        new Thread(futureTask).start();
        String a = (String)futureTask.get();//获取Callable的返回值
        System.out.println(a); */
    }
}
class MyThread implements Callable<String> {
    @Override
    public String call() throws Exception {
        for(int i=0; i<100; i++) {
            System.out.println(Thread.currentThread().getName() + i);
        }
        return "sb";
    }
}

class MyThread3 extends Thread {
    @Override
    public void run() {
        System.out.println();
    }
}

class MyThread4 implements Runnable {
    @Override
    public void run() {
        System.out.println();
    }
}

class MyThread5 implements Callable<String> {
    @Override
    public String call() throws Exception{

        return "sb";
    }
}
