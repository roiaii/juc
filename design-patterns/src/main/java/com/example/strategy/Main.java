package com.example.strategy;

import java.util.Arrays;

/**
 * 策略模式：用来封装算法，对各种算法类和使用算法的类进行解耦
 *
 * 需求：简单收银系统，收费方式有：直接收费、打折、满减等，根据单价、收费方式、数量得出最后应付金额。
 */

// 客户端类
public class Main {
    public static void main(String[] args) {
        CashContext cs = new CashContext("打8折");
        double result = cs.getResult(100);
        System.out.println(result);
    }

}
