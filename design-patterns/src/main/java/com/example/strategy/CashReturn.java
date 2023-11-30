package com.example.strategy;

// 满多少减多少
public class CashReturn extends CashSuper{
    private double moneyCondition = 0;
    private double moneyReturn = 0;

    public CashReturn(String moneyCondition, String moneyReturn) {
        this.moneyCondition = Double.parseDouble(moneyCondition);
        this.moneyReturn = Double.parseDouble(moneyReturn);
    }
    @Override
    public double acceptCash(double money) {
        double result = money;
        if (money >= moneyCondition) {
            result = money - moneyReturn;
        }
        return result;
    }
}
