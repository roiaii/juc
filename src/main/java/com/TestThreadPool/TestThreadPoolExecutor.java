package com.TestThreadPool;

import java.sql.SQLOutput;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

import java.util.concurrent.ThreadPoolExecutor;

public class TestThreadPoolExecutor {
    public static void main(String[] args) {
        //使用ThreadPoolExecutor创建线程池，Runnable创建线程
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 1L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(80), new ThreadPoolExecutor.CallerRunsPolicy());

        for(int i=0; i<100; i++) {
            Runnable worker = new MyRunnable();
            executor.execute(worker);
        }

        executor.shutdown();

    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        try{
            Thread.sleep(500);
        } catch (Exception e) {

        }
        System.out.println(Thread.currentThread().getName() + "test");
    }
}
