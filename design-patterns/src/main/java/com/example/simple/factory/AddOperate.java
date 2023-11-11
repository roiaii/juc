package com.example.simple.factory;

public class AddOperate extends Operate {
    @Override
    public double getResult() {
        return getNumberA() + getNumberB();
    }
}
