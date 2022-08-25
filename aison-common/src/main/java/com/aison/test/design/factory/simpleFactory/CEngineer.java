package com.aison.test.design.factory.simpleFactory;

public class CEngineer implements JobContent{
    @Override
    public String study() {
        return "我也不知道学啥";
    }

    @Override
    public String work() {
        return "干最底层";
    }

    @Override
    public void enjoy() {
        System.out.println("不停干活");
    }
}
