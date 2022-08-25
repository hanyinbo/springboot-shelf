package com.aison.test.design.factory.factoryMethod;
/**
  * 不同产品实现公共接口
  * @author hyb
　* @date 2022/8/23 10:12
  */
public class CEngineer implements JobContent {
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
