package com;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class TestClassStudent {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 自定义对象 重写 equals（）和 hashCode（）方法
    // 如果该对象作为Map的key需要重写以上两个方法
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestClassStudent that = (TestClassStudent) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }


    public static void main(String[] args) {
        TestClassStudent testClassStudent1 = new TestClassStudent();
        testClassStudent1.setId(1l);
        testClassStudent1.setName("lijun");

        TestClassStudent testClassStudent2 = new TestClassStudent();
        testClassStudent2.setId(1l);
        testClassStudent2.setName("lijun");

        Student student1 = new Student();
        student1.setId(1l);
        student1.setName("lijun");

        Student student2 = new Student();
        student2.setId(1l);
        student2.setName("lijun");

        // 两个对象内容相同
        System.out.println(student1.equals(student2)); // false，没有重写，比较的是指针
        System.out.println(testClassStudent1.equals(testClassStudent2)); // true，重写，比较对象的内容

        // 测试hashmap put元素的过程，首先计算hashcode，然后判断是否发生哈希冲突，调用equals方法判是否是同一个元素
        HashMap<TestClassStudent, String> map1 = new HashMap<>();
        HashMap<Student, String> map2 = new HashMap<>();

        map1.put(testClassStudent1, testClassStudent1.getName());
        System.out.println(map1.containsKey(testClassStudent2)); // true，两者hashcode以及equals是一样的
        map1.put(testClassStudent2, testClassStudent2.getName());
        System.out.println(map1.size() + ":" + map1.get(testClassStudent1)); // 1 : lijun

        map2.put(student1, student1.getName());
        System.out.println(map2.containsKey(student2)); // false，两者hashcode以及equals是不同的
    }
}


class Student {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
