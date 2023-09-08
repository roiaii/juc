package com.example.juc.singleton;

/**
 * 相关链接; https://mp.weixin.qq.com/s/1AbZGp6uwCNR0R3TYdDRlg
 *
 * 手写单例模式
 * 关键点：1、私有构造方法
 *      为什么要改为私有的，因为单例模式就是要保证只用一个实例，public就会随意new对象，所以改为private
 *
 *          2、静态私有变量
 *          定义一个静态私有变量，也就是我们唯一的实列变量，只允许内部访问
 *
 *          3、静态共有法方法
 *          提供一个静态共有方法，保证全局唯一的访问点
 *
 */

//public class Singleton {

    /*
    public static void main(String[] args) {
        for(int i=0; i<100000; i++) {
            new Thread(()->{
                System.out.println(Singleton.getInstance().hashCode());}).start();
        }
    }*/

    //懒汉式
    //延迟创建实例对象，在第一次调用时再创建
    /*
    private static Singleton singleton = null;
    private Singleton() {}
    public static Singleton getInstance() {
        if(singleton == null){
            singleton = new Singleton();
        }
        return singleton;  //为什么会线程不安全呢？因为会返回不同的哈希码，说明创建了不只一个对象
                        //为什么会创建不只一个对象，假如有两个线程，线程a第一次获取对象，发现为空，就会去
                        //实例化对象，但是在线程a还没有实例化完成的时候，线程b来获取实例对象，发现仍然为空，
                        //就会去实例化对象，此时，线程a实例化完成，并返回对象内存地址，这时候，线程b也实例化完成，
                        //并返回第二个内存地址，所以就会出现不止一个对象实例。
    } */

    //饿汉式
    //类加载的时候就创建了静态类变量实例
    /*
    private static Singleton singleton = new Singleton();

    private Singleton(){}
    public static Singleton getInstance() {
        return singleton;
    } */

    //线程安全的懒汉式（Synchronized）
    /*
    private static Singleton singleton = null;

    private Singleton(){}
    public static synchronized Singleton getInstance(){
        if(singleton == null){ //不仅是第一次创建实例的时候会发生线程阻塞，后面每次获得实例也会出现线程阻塞
            singleton = new Singleton();
        }
        return singleton;
    } */


    //双重检查锁实现，懒汉式
    //只有在第一次实例化的时候有可能出现下称阻塞，因为使用了双重检查
    //当实例化变量后，多个线程压根就不会进去到获取锁的那一步。
 //   private volatile static Singleton singleton = null;
    /*
    为什么用volatile修饰， 因为new Singleton时，需要三步，1、实例化 2、初始化 3、将引用指向实例
    如果jvm对指令重排序优化，那么有可能执行顺序为132，此时好没有初始化。如果一个线程执行了13还没来得及2，此时过来
    另一个线程获取实例，发现不为空，结果取得的实例还未初始化，会报错。
    因此，采用volatile保证变量禁止指令重排序（有序性）//用到了volatile的有序性而不是可见性
    */
 /*   private Singleton() {}
    public static Singleton getInstance() {
        if(singleton == null) {
            synchronized(Singleton.class) { //同步代码块
                if(singleton == null)
                    singleton = new Singleton();
            }
        }
        return singleton;
    }

} */


 /*
//再写一遍
    //懒汉式、饿汉式、线程安全的懒汉式、双重锁检查懒汉式（避免多余的阻塞操作带来的额外的开销）
class Singleton {
    //懒汉式
    //在第一次使用时才实例化

    private static Singleton singleton = null;
    private Singleton(){}
    public static Singleton getInstance() {
        if(singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }

    //饿汉式
    //一开始就实例化
    /*
    private static Singleton singleton = new Singleton();
    private Singleton(){}

    public static Singleton getInstance() {
        return singleton;
    } */

    //线程安全的懒汉式
    /*
    private static Singleton singleton = null;
    private Singleton(){}

    public static synchronized Singleton getInstance() {
        if(singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }
     */
/*
    private volatile static Singleton singleton = null;
    private Singleton(){}

    public static Singleton getInstance() {
        if(singleton == null) {
            synchronized(Singleton.class) {
                if(singleton == null) {         //双重锁，避免多余的阻塞操作
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    } */



    // 再写一遍
class Singleton {

    //懒汉式 在第一次使用的时候才实例化对象
    private static Singleton singleton = null;
    private Singleton(){} //静态构造器
    public static Singleton getInstance() {
        if(singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }


    /*
    //饿汉式 一开始初始化对象
    private static Singleton singleton = new Singleton();  //在类初始化的时候就创建了实例，天生线程安全
    private static Singleton() {}
    public static Singleton getInstance() {
        return singleton;
    } */

    //线程安全的 懒汉式
    /*
    private static Singleton singleton = null;
    private Singleton() {}
    public static synchronized Singleton getInstance() {  //锁的粒度大，效率低
        if(singleton == null) {  //             并且不光第一创建会竞争锁，以后每次调用getInstance()都会竞争锁
            singleton = new Singleton();
        }
        return singleton;
    } */


    /*

    private static volatile Singleton singleton = null;  //为什么加volatile 因为new Singlton()是非原子操作
                                            //      需要三步：1、分配内存 2、构造器初始化实例 3、将引用指向实例内存
    private Singleton() {}

    public static Singleton getInstance() {
        if(singleton == null) {
            synchronized(Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
     */

    //再写一遍
    //懒汉式（线程不安全）
    /*
    private static Singleton singleton = null;
    private Singleton(){}
    public static Singleton getSingleton() {
        if(singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    } */

    /*
    //饿汉式  //不是懒加载，浪费内存空间
    private static Singleton singleton = new Singleton();
    private Singleton(){}

    public static Singleton getSingleton() {
        return singleton;
    } */

    //懒汉式（线程安全）
    /*
    private static Singleton singleton = null;
    private Singleton(){}

    public static synchronized Singleton getSingleton() {
        if(singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    } */

    //懒汉式（双重检查锁）

    /**
     * 问题1：为什么还需要第二次判断是否为null？
     * 线程1拿到锁执行完new一个对象的前两步（符合有序性），此时为null，线程2尝试获取锁失败，线程1执行完成释放锁，线程2继续执行，
     * 此时，不为null，如果步进行二次检查，线程2会创建出一个新的对象。
     *
     * 问题2: 为什么需要volatile修饰？
     * new一个对象分为3步，1分配内存 2初始化 3引用指向内存 反证 线程1拿到锁，假如执行顺序为132，在执行完13后，线程2发现不为null
     * 会直接返回对象，但是对象还未初始化。
     * @param args
     */
    /*
    private static volatile Singleton singleton = null;
    private Singleton(){}

    public static Singleton getInstance() {
        if(singleton == null) {
            synchronized(Singleton.class) {
                if (singleton == null) { // 为什么还需要第二次判断是否为null？
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    } */






    public static void main(String[] args) {




        for(int i=0; i<100000; i++) {
            new Thread(()-> {
                System.out.println(Thread.currentThread().getName()+":"+Singleton.getInstance().hashCode());
                try{
                    Thread.sleep(1000);
                }catch(Exception e){

                }
            }).start();
        }
    }
}
