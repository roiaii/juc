package com.example.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MapTest {
    public static void main(String[] args) {
        //Map<String, String> map = new HashMap<>();//java.util.ConcurrentModificationException
        //工具类类似set list
        Map<String, String> map = new ConcurrentHashMap<>();//juc 原理  完美解决线程同步
        for(int i=0; i<20; ++i){
            new Thread(()->{
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0,5));
                System.out.println(map);
            }).start();
        }
    }
}
