package com.example.juc.Proxy;

import org.junit.platform.commons.util.ClassLoaderUtils;
import sun.misc.ClassLoaderUtil;

import java.lang.reflect.Proxy;

/**
 * @Description : 测试使用动态代理
 *              动态代理的理解：通过对代理类的访问间接访问被代理的对象，让被代理的对象专注于做自己的事
 *              同时，代理类还可以对被代理的对象做一些增强处理
 * @Author : jun1.li
 * @Date : 2022/12/12 21:31
 */
public class TestProxy {
    public static void main(String[] args) {
        //构造代理器
        JDKProxy jdkProxy = new JDKProxy(new RealHello());

        //ClassLoader classLoader = ClassLoaderUtils.getDefaultClassLoader();

        Hello hello = (Hello)jdkProxy.getProxy(Hello.class);

        System.out.println(hello.say1());

    }
}
