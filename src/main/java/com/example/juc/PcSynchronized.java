package com.example.juc;

//使用synchronized来模拟实现 生产者消费者模型

/**
 * 注意虚假唤醒问题
 * 相关博客解释 https://www.cnblogs.com/jichi/p/12694260.html
 */

public class PcSynchronized {
    public static void main(String[] args) {
        Data data = new Data();

        //生产者线程
        new Thread(()->{
            for(int i=0; i<10; i++){
                data.produce();
                try{
                    Thread.sleep(1000);
                }catch(Exception e){
                    e.printStackTrace();
                }

            }

        }).start();

        new Thread(()->{
            for(int i=0; i<5; i++)
                data.customer();
        }).start();
        new Thread(()->{
            for(int i=0; i<4; i++)
                data.customer();
        }).start();
    }

    static class Data{
        int count = 0;

        public synchronized void produce(){
            while(count != 0){
                try{
                    this.wait();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            count++;
            System.out.println(Thread.currentThread().getName() + "生产了" + count);
            this.notifyAll();
        }

        public synchronized void customer(){
            while(count == 0){
                try{
                    this.wait();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "消费了" + count--);
            this.notifyAll();
        }
    }
}







/*
//在写一遍，用Synchronized实现生产者、消费者
public class PcSynchronized {
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(()->{
            for(int i=0; i<10; i++) {
                data.customer();
            }
        },"B").start();
        new Thread(()->{
            for(int i=0; i<10; i++) {
                data.produce();
            }
        }, "A").start();
    }

    static class Data {
        private int count = 0;

        public synchronized  void produce() {
            while(count != 0) {
                try {
                    this.wait();//缓存区有数据，线程阻塞
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            count++;
            System.out.println(Thread.currentThread().getName() + count);
            this.notifyAll();
        }

        public synchronized void customer() {
            while(count == 0) {
                try {
                    this.wait();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            count--;
            System.out.println(Thread.currentThread().getName() + count);
            this.notifyAll();
        }
    }
} */




/*
public class PcSynchronized {
    public static void main(String[] args) {
        //创建资源类
        Data data = new Data();

        //创建多个线程去访问共享资源
        new Thread(()->{for(int i=0; i<20; ++i) data.increase();}).start();
        new Thread(()->{for(int i=0; i<20; ++i) data.increase();}).start();
        new Thread(()->{for(int i=0; i<20; ++i) data.descend();}).start();
        new Thread(()->{for(int i=0; i<20; ++i) data.descend();}).start();
    }
}

//资源类
class Data{
    private int count = 0;

    //实现生产方式
    public synchronized void increase(){//加锁锁住同步方法
        while(count != 0){ //缓存区有数据时，阻塞
            //等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //满足条件
        count++;
        System.out.println(Thread.currentThread().getName()+"->"+count);
        //通知该对象上的所有线程来消费
        this.notifyAll();
    }
    //消费者方法
    public synchronized void descend(){
        while(count == 0){
            try {
                this.wait(); //本Object.wait()直接调用
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //满足条件
        count--;
        System.out.println(Thread.currentThread().getName()+"->"+count);
        this.notifyAll();
    }
}
*/