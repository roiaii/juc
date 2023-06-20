package com.map;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMap 的 key、 value都不能为null
 * 原因：首先value不能为null，因为在get（Object）后为null，无法通过containsKey方法解决二义性问题，
 * 因为通常在ConcurrentHashMap用在并发环境下，假如get方法和containsKey方法中间有其他线程增加或者删除一个元素，是无法解决二义性问题的
 *
 * 至于key也是不能为null，因为源码中就是这样写的，会先判断key value是否为null
 */
public class TestCurrentHashMap {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        //concurrentHashMap.put(null, "3");
        //System.out.println(concurrentHashMap.get(null));

        concurrentHashMap.put("3", null);
        System.out.println(concurrentHashMap.get("3"));
    }
}
