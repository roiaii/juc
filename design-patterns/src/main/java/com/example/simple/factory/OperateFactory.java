package com.example.simple.factory;

public class OperateFactory {

    public static Operate createOperate (String operate) {
        Operate ope = null;
        switch (operate) {
            case "+" :
                ope = new AddOperate();
                break;
            case "-" :
                ope = new SubOperate();
                break;
        }
        return ope;
    }
}
