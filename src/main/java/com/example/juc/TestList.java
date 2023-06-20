package com.example.juc;

import sun.security.util.AbstractAlgorithmConstraints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestList {
    public static void main(String[] args) {
        //List<String> list = new CopyOnWriteArrayList<>();//使用juc解决
        List<String> list = Collections.synchronizedList(new ArrayList<>());//使用工具类解决
        //List<String> list = new ArrayList<>();//会报错 并发修改异常
        for(int i=0; i<50; ++i){
            new Thread(()->{
                list.add("sddsv");
                System.out.println(list);
            }).start();
        }
    }
}
