package com.example.juc.ThreadPool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;


/**
 *
 * 参考链接：https://juejin.cn/post/6968721240592744455
 */
public class MyThreadPool {

    BlockingQueue<Runnable> taskQueue;
    List<MyThread> threads;//

    public MyThreadPool(BlockingQueue<Runnable> taskQueue, int threadSize) {
        this.taskQueue = taskQueue;
        threads = new ArrayList<MyThread>(threadSize);

        //初始化线程
        for(int i=0; i<threadSize; i++) {
            MyThread thread = new MyThread("my-task-thread-" + i);
            thread.start();
            threads.add(thread);
        }
    }

    //将任务塞进任务队列
    public void execute(Runnable task) throws InterruptedException {
        taskQueue.put(task);
    }



    //内部自定义实现线程类
    class MyThread extends Thread {

        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (true) { //死循环
                Runnable task = null;
                try {
                    task = taskQueue.take(); //不断从任务队列获取任务
                } catch (InterruptedException e) {
                    //logger.error("记录点东西.....", e);
                }
                task.run(); //执行
            }
        }
    }
}

class TestThreadPool {
    public static void main(String[] args) {
        /*
        MyThreadPool myThreadPool = new MyThreadPool(new LinkedBlockingQueue<>(10), 3);
        IntStream.rangeClosed(1, 5).forEach((i)-> {
            try {
                myThreadPool.execute(()-> {
                    System.out.println(Thread.currentThread().getName() + " 我是测试类");
                });
            } catch (InterruptedException e) {
                //logger.error("记录点东西.....", e);
            }
        });
         */


        //线程池实验
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 5, 0L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.DiscardPolicy());


         Data data = new Data();
         ExecutorService executor = Executors.newFixedThreadPool(3);  //服用固定线程池数，核心线程池数1，无界队列
         for(int i=0; i<99; i++){
             Future future = threadPoolExecutor.submit(new Runnable() {
                 @Override
                 public void run() {
                     System.out.println(Thread.currentThread().getName() + " " + data.idx++);
                 }
             });
         }
         executor.shutdown();




    }

    static class Data{
        int idx = 0;

    }
}