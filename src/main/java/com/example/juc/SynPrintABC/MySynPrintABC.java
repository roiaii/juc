package com.example.juc.SynPrintABC;

import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;

import java.io.PrintStream;
import java.util.ConcurrentModificationException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


//3.17再写一遍
public class MySynPrintABC {
    public static void main(String[] args) {
        Print print = new Print();
        new Thread(() -> {
            for (int i = 0; i < 100; i++)
                print.A();
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++)
                print.B();
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++)
                print.C();
        }).start();
    }

    static class Print {
        private int flag = 1;
        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();

        public void A() {
            lock.lock();
            try {
                while (flag != 1) {
                    condition1.await();
                }
                System.out.println(Thread.currentThread().getName() + "lock A");
                flag = 2;
                condition2.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void B() {
            lock.lock();
            try {
                while (flag != 2) {
                    condition2.await();
                }
                System.out.println(Thread.currentThread().getName() + "lock B");
                flag = 3;
                condition3.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void C() {
            lock.lock();
            try {
                while (flag != 3) {
                    condition3.await();
                }
                System.out.println(Thread.currentThread().getName() + "lock C");
                flag = 1;
                condition1.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    //Synchronized 实现多线程下对共享资源的顺序访问
    static class Print1 {
        int count = 1;

        void A() {
            synchronized (this) {
                while (count != 1) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                count = 2;
                System.out.println(Thread.currentThread().getName() + "Synchronized A");
                this.notifyAll();

            }
        }

        synchronized void B() {
                while (count != 2) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                count = 3;
                System.out.println(Thread.currentThread().getName() + "Synchronized B");
                this.notifyAll();

        }

        void C() {
            synchronized (this) {
                while (count != 3) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                count = 1;
                System.out.println(Thread.currentThread().getName() + "Synchronized C");
                this.notifyAll();
            }
        }
    }
}









/*
public class MySynPrintABC {
    public static void main(String[] args) {
        Print print = new Print();
        new Thread(() -> {
            for(int i=0; i<2; i++) {
                print.A();
            }
        }).start();
        new Thread(() -> {
            for(int i=0; i<2; i++) {
                print.B();
            }
        }).start();

        new Thread(() -> {
            for(int i=0; i<2; i++) {
                print.C();
            }
        }).start();
    }
}

class Print{
    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();
    int count = 1;
    public void A(){
        try{
            while(count != 1){
                //condition1.await();
            }
            System.out.println(Thread.currentThread().getName() + "AAA");
            count = 2;
            //condition2.signal();
        } catch (Exception e){
            e.printStackTrace();
        } finally{
        }
    }

    public void B(){
        try{
            while(count != 2){
                //condition2.await();
            }
            System.out.println(Thread.currentThread().getName() + "BB");
            count = 3;
            //condition3.signal();
        } catch (Exception e){
            e.printStackTrace();
        } finally{
        }
    }

    public void C(){
        try{
            while(count != 3){
                //condition3.await();
            }
            System.out.println(Thread.currentThread().getName() + "C");
            count = 1;
            //condition1.signal();
        } catch (Exception e){
            e.printStackTrace();
        } finally{
        }
    }
} */







//再写一遍，循环打印ABC，也就是三个线程有序执行
//打印出来是 AAA BB C AAA BB C
/*
public class MySynPrintABC{
    public static void main(String[] args) {
        //创建共享资源类
        Print print = new Print();
        //开启三个线程，实现多线程
        new Thread(() -> {
            for(int i=0; i<2; i++)
                print.A();
        }).start();

        new Thread(() -> {
            for(int i=0; i<2; i++)
                print.B();
        }).start();

        new Thread(() -> {
            for(int i=0; i<2; i++)
                print.C();
        }).start();

    }
}

class Print{
    private int flag = 1;

    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void A(){
        lock.lock();
        try{
            while(flag != 1) {
                condition1.await();
            }
            for(int i=0; i<3; i++)
            System.out.println(Thread.currentThread().getName() + "A");
            flag = 2;
            condition2.signal();
        } catch(Exception e) {
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }

    public void B(){
        lock.lock();
        try{
            while(flag != 2){
                condition2.await();
            }
            for(int i=0; i<2; i++)
            System.out.println(Thread.currentThread().getName() + "B");
            flag = 3;
            condition3.signal();
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }
    public void C(){
        lock.lock();
        try{
            while(flag != 3){
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName() + "C");
            flag = 1;
            condition1.signal();
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }
} */








//再写一遍
//循环打印ABC 三个线程要有执行顺序，那就用lock+Condition
//A打印3次，B打印2次，C打印1一次 打印两轮
/*
public class MySynPrintABC {
    public static void main(String[] args) {
        Print print = new Print();

        //开启三个多线程
        new Thread(()->{
            for(int i=0; i<2; i++) {
                print.A();
            }
        }).start();
        new Thread(()->{
            for(int i=0; i<2; i++) {
                print.B();
            }
        }).start();
        new Thread(()->{
            for(int i=0; i<2; i++) {
                print.C();
            }
        }).start();
    }
}

//共享资源类
class Print{
    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();  //lock 和 Condition 配合使用实现靳准唤醒

    int count  = 1; //阻塞标志

    public void A() {
        lock.lock();
        try {
            while(count != 1) {
                condition1.await(); //阻塞
            }
            count = 2;
            condition2.signal();
            for(int i=0; i<3; i++)
            System.out.println(Thread.currentThread().getName() + "A");
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void B() {
        lock.lock();
        try {
            while(count != 2) {
                condition2.await(); //阻塞
            }
            count = 3;
            condition3.signal();
            for(int i=0; i<2; i++)
            System.out.println(Thread.currentThread().getName() + "B");
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void C() {
        lock.lock();
        try {
            while(count != 3) {
                condition3.await(); //阻塞
            }
            count = 1;
            condition1.signal();
            System.out.println(Thread.currentThread().getName() + "C");
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
} */






/**
 * 三个线程ABC交替打印ABC
 */

/**
 * 实现三个线程按照ABC顺序打印，循环10轮
 */

/*
public class MySynPrintABC {
    public static void main(String[] args) {
        Print print = new Print();
        new Thread(()->{
            for(int i=0; i<10; i++) {
                print.A();
            }
        }, "A").start();
        new Thread(()->{
            for(int i=0; i<10; i++) {
                print.B();
            }
        }, "B").start();
        new Thread(()->{
            for(int i=0; i<10; i++) {
                print.C();
            }
        }, "C").start();
    }
} */

/**
 *使用Condition精准唤醒阻塞的线程
 * 对三个方法加锁
 */

/*
class Print {
    private int count = 1;
    Lock lock = new ReentrantLock();

    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void A() {
        lock.lock();
        try {
            while(count != 1){
                condition1.await();
            }
            count = 2;
            System.out.println(Thread.currentThread().getName() + " : AAA");
            condition2.signal();
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            lock.unlock();
        }

    }

    public void B() {
        lock.lock();
        try {
            while(count != 2){
                condition2.await();
            }

            count = 3;
            System.out.println(Thread.currentThread().getName() + " : BBB");
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
          lock.unlock();
        }
    }

    public void C() {
        lock.lock();
        try {
            while(count != 3) {
                condition3.await();
            }

            count = 1;
            System.out.println(Thread.currentThread().getName() + " : CCC");
            condition1.signal();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
} */
