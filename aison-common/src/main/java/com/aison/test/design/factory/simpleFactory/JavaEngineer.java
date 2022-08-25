package com.aison.test.design.factory.simpleFactory;

public class JavaEngineer implements JobContent {
    @Override
    public String study() {
        return "学习liunx、mysql、design";
    }

    @Override
    public String work() {
        return "负责后端开发";
    }

    @Override
    public void enjoy() {
        System.out.println("我是最牛批的那个吧");
    }
}
