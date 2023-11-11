package com.example.simple.factory;


import java.util.Arrays;

/**
 * 需求：实现计算器，输入两个数，一个运算符，得到运算结果
 * 简单工厂模式
 */

/**
 * 设计模式去围绕可扩展、可维护、可复用去理解
 * 简单工厂模式，创建不同类型对象的过程交给工厂类去做（解耦）
 * 这里面对 继承、封装和多态的理解
 * 设计抽象类计算类，不同类型的计算去继承抽象类，并重写自己的计算方法（多态）
 */
public class SimpleFactory {
    public static void main(String[] args) {
        Operate operate = null;
        operate = OperateFactory.createOperate("-");
        operate.setNumberA(1);
        operate.setNumberB(1);
        double result = operate.getResult();
        System.out.println("result = " + result);
    }
}
