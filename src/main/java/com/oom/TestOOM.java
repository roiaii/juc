package com.oom;

import java.util.ArrayList;

public class TestOOM {
    byte[] array = new byte[1*1024*1024];//1m

    public static void main(String[] args) {
        ArrayList<TestOOM> list = new ArrayList<>();
        int count =0;
        try {
            while(true){
                list.add(new TestOOM());
                ++count;
            }
        } catch (Exception e) {
            System.out.println(count);
            e.printStackTrace();
        }
    }
}
