package com.example.strategy;

// 直接收取
public class CashNormal extends CashSuper{

    @Override
    public double acceptCash(double money) {
        return money;
    }
}
