package com.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/***
 * 手写一个反射小demo
 * 为了学习如何使用反射，以及反射这种机制有哪些好处、坏处
 */
public class ReflectExample {
    public static void main(String[] args) {
        Student student = new Student("test", 20);

        try {
            //获取.Class文件，共三种方式
            //1、Class.forName("Student.class")
            //2、Student.class
            //3、Student.getClass()
            Class<?> clazz = Class.forName("com.reflect.Student");// Student.class;

            //获取构造器方法、方法、字段
            Constructor<?> constructor = clazz.getConstructor(String.class, int.class); //这里参数是指的该构造器方法中参数的类型，就获取该参数类型列表下的构造器
            Method methodPrint = clazz.getMethod("print");
            Method methodGet = clazz.getMethod("getAge");
            System.out.println(methodPrint.getName() + " : " + methodGet.getName());  //  Class类中方法名打印
            Field name = clazz.getField("name");  //获取的是该Class对象中的字段的定义，在哪个类路径下中的哪个字段名
            Field age = clazz.getField("age");

            System.out.println("获取的对象中的字段值为"  + name + " : " + age);

            //根据.Class文件，实例化对象
            Student newInstance = (Student)constructor.newInstance("lijun", 25);
            //调用方法
            System.out.println("执行获取的对象的实例方法print：");
            methodPrint.invoke(student);  //可以在运行时执行实例对象的方法
            System.out.println("执行通过反射实例化的对象的实例方法print：");
            methodPrint.invoke(newInstance);  //执行的是通过反射机制自己实例化的对象的方法

            //还可以在运行时获取现有的实例对象，并修改它的字段"

            int t = (int)methodGet.invoke(newInstance);
            System.out.println("执行获取对象的实例方法getAge后，返回值为：" + t);

            //获取有参方法
            Method methodSum = clazz.getMethod("sum", int.class, int.class);
            //执行有参数方法 //无返回值
            methodSum.invoke(student, 1, 2);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}


//定义被动态获取的class对象
class Student{
    public String name;
    public int age;

    public Student(String name, int age){
        this.name = name;
        this.age = age;
    }

    public void print(){
        System.out.println("姓名：" + name + "年龄：" + age);
    }

    public int getAge(){
        return age;
    }
    //有参方法
    public void sum(int a, int b){
        System.out.println("a = " + a + ", b = " + b + "    a + b = " + (a+b));
    }
}
