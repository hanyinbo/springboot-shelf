package com.aison.test.design.factory.factoryMethod;
/**
  * 不同产品实现公共接口
  * @author hyb
　* @date 2022/8/23 10:14
  */
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
