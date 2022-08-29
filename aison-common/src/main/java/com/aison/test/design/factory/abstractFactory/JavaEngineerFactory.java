package com.aison.test.design.factory.abstractFactory;
/**
  * 工厂实现类
  * @author hyb
　* @date 2022/8/26 16:48
  */
public class JavaEngineerFactory implements IProductFactory {
    @Override
    public HardWork createWork() {
        return new JavaPrimaryEngineer();
    }

    @Override
    public Advising createAdvising() {
        return new JavaSeniorEngineer();
    }
}
