package com.example.juc;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunnableTest {

    public static void main(String[] args) {
        //Thread类实现Runnable接口，是一种静态代理
        new Thread(new MyThread2(), "A").start();
        new Thread(new MyThread2(), "B").start();

        //测试，讲台代理，调用start方法开始两个线程，实现多线程的效果
        //每个线程的任务就是打印100次
        //所以AB线程会交替执行。
    }

}

/*
class  MyThread2 implements Runnable{
    @Override
    public void run() {
        for(int i=0; i<100; i++) {
            System.out.println(Thread.currentThread().getName() + i);
        }
    }
} */

//实现多线程采用实现Runnable接口
class MyThread2 implements Runnable {
    @Override
    public void run() {
        for(int i=0; i<100; i++) {
            System.out.println(Thread.currentThread().getName() + i);
        }
    }
}
