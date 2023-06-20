package com.example.juc.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @Description : jdk代理类生成器
 * @Author : jun1.li
 * @Date : 2022/12/12 21:36
 */
public class JDKProxy implements InvocationHandler {
    private Object target;

    public JDKProxy(Object target){
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //增强处理的逻辑 //也是实现AOP的关键
        System.out.println("代理方法："+method.getName());
        System.out.println("代理类做的事情");
        //通过反射，执行被代理的对象。
        Object o = method.invoke(target, args);
        return o;
    }

    public <T> T getProxy(Class<T> clazz){
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return(T)o;
    }
}
