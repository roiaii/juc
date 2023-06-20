package com.map;

import java.util.HashMap;

/**
 * HashMap的key、value可以为null
 * 可以通过containsKey方法来解决二义性问题
 */
public class TestHashMap {
    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<>();
        System.out.println(hashMap.get("3"));  // null
        System.out.println(hashMap.containsKey("3"));  // false
        hashMap.put("3", null);
        System.out.println(hashMap.get("3"));  //null
        System.out.println(hashMap.containsKey("3"));  //true  //可以通过containsKey方法区分二义性问题


        System.out.println(hashMap.containsKey("null")); //false
        hashMap.put(null, "3");
        System.out.println(hashMap.containsKey(null)); // true
        System.out.println(hashMap.get(null)); // "3"
    }
}
