package com.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.*;
import java.util.Arrays;

/**
 * 手写动态代理，便于更加充分的理解动态代理机制
 * 动态代理是基于反射机制实现的
 * 动态代理是AOP思想的践行者
 */

/**
 * 同理，Spring框架中的Spring AOP 、 AspectJ 也是AOP思想的践行者
 * 前者通过两种方式实现代理 JDK动态代理 和 CGlib代理
 * JDK动态代理是基于Java反射的机制实现的，需要目标对象实现接口，而后者是通过为
 * 目标对象生成一个子类，通过重写父类中的方法来实现代理，没有涉及到反射，性能会稍好些
 *
 * 此外AspectJ代理是在编译时已经完成对字节码的修改，而Spring AOP是在运行是生成代理对象
 */


/**
 * 其实Java中的动态代理和JDK动态代理是一样的
 * 只不过在Spring框架中实现AOP，会封装的更好些，比如切面会有很丰富（InvocationHandler就相当于一个切面），切点等，并且
 * 采用注解的方式，程序员编写会更方便，并且会更容易管理这些切面和切点
 */



public class ProxyDemo {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        UserInvocationHandler handler = new UserInvocationHandler(userService);

        //生成代理对象
        UserService proxy = (UserService) Proxy.newProxyInstance(
                userService.getClass().getClassLoader(),
                userService.getClass().getInterfaces(),
                handler
        );

        //通过代理对象执行目标对象中的方法
        proxy.addUser("test");
        proxy.delateUser("lijun");  //思考下调用这个方法的执行链路

    }
}
//实现InvocationHandler接口
class UserInvocationHandler implements InvocationHandler {
    Object target = null;
    public UserInvocationHandler(Object target){
        //将要代理的目标对象赋给target
        this.target = target;
    }

    @Override
    //重写invoke方法
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName() + "方法执行前的逻辑。。");
        Object result = method.invoke(target, args);
        System.out.println(method.getName() + "方法执行后的逻辑。。");
        return result;
    }
}




//创建业务接口
interface UserService{
    void addUser(String name);
    void delateUser(String name);
}

//实现接口的类
class UserServiceImpl implements UserService{
    @Override
    public void addUser(String name){
        System.out.println("add " + "name = " + name);
    }
    @Override
    public void delateUser(String name){
        System.out.println("delate name = " + name);
    }
}
