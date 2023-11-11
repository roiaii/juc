package com.example.simple.factory;

public class SubOperate extends Operate {

    @Override
    public double getResult() {
        return getNumberA() - getNumberB();
    }
}
