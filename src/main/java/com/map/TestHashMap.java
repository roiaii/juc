package com.map;

import java.util.HashMap;

/**
 * HashMap的key、value可以为null
 * 可以通过containsKey方法来解决二义性问题
 */
public class TestHashMap {
    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<>();
        // 证明 value可以为null
        System.out.println(hashMap.get("3"));  // null
        System.out.println(hashMap.containsKey("3"));  // false
        hashMap.put("3", null);
        System.out.println(hashMap.get("3"));  //null
        System.out.println(hashMap.containsKey("3"));  //true  //可以通过containsKey方法区分二义性问题


        // 证明 key可以是null
        // 其中key为null，所对应的hashCode为0，将value放在哈希桶的第0个位置
        System.out.println(hashMap.containsKey("null")); //false
        hashMap.put(null, "3");
        System.out.println(hashMap.containsKey(null)); // true
        System.out.println(hashMap.get(null)); // "3"

        // 两者均是通过containsKey解决 二义性问题
    }
}
