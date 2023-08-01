package com.NPE;

/**
 * 该代码主要用来测试常见NPE问题
 */

import com.google.common.collect.Lists;

import javax.jnlp.IntegrationService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 *
 * 常见五种NPE场景
 * 1、包装类型自动拆箱自动装箱 采用Optional.ofNullable().orElse()加默认值的方式解决
 * 2、空字符串进行比较 将有可能为null的Object放在前面
 * 3、A对象引用B对象，从A对象获取B对象后，没有对B对象进行判空就访问内部变量 记得判空 Collections.isNotEmpty()
 * 4、Function返回的List是null而不是空，没有判断就使用 记得判空，或者用Optional.ofNullable().orElse()加默认值来放置出现NPE
 * 5、currentHashMap的k,v不能为null
 */

public class NPETest {
    public static void main(String[] args) {
        System.out.println(Optional.ofNullable(null).orElse(Lists.newArrayList(1)));
        System.out.println(wrongFunction(null, 1).size());
    }

    private static List<Integer> wrongFunction(String s, Integer i) {
        i++;
        System.out.println(i);
        s.equals("ok");
        return Lists.newArrayList();
    }
}
