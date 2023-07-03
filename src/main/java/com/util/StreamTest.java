package com.util;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 测试Stream各种常见用法
 */
public class StreamTest {

    public static void main(String[] args) {
        List<String> lists = Lists.newArrayList();
        for (int i=0; i<10; i++) lists.add(String.valueOf(i));

        List<Integer> a = lists.stream().map(e->{  // stream只是将原来的lists输出为流并进行计算，不改变原来的lists
            return 2;
        }).collect(Collectors.toList());
        System.out.println(a);
        System.out.println(lists);
    }
}
