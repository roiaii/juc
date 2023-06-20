package com.example.juc;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

public class SetTest {
    public static void main(String[] args) {
        //Set<String> set = new HashSet<>();//java.util.ConcurrentModificationException 并发修改异常了
        //Set<String> set = Collections.synchronizedSet(new HashSet<>());//工具类解决
        Set<String> set = new CopyOnWriteArraySet<>();//juc解决
        for(int i=0; i<50; ++i){
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(set);
            }).start();
        }
    }
}
