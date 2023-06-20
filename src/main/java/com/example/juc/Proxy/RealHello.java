package com.example.juc.Proxy;

/**
 * @Description : 真实调用的对象
 * @Author : jun1.li
 * @Date : 2022/12/12 21:34
 */
public class RealHello implements Hello{
    public String say(){
        return "i'm a proxy";
    }

    @Override
    public String say1() {
        return "i'm say1!";
    }
}
