package com.aison.test.design.factory.abstractFactory;
/**
  * 工厂接口类
  * @author hyb
　* @date 2022/8/26 16:46
  */
public interface IProductFactory {
    public HardWork createWork();

    public Advising createAdvising();
}
