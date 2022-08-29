package com.aison.test.design.factory.abstractFactory;

/**
  * 接口实现类
  * @author hyb
　* @date 2022/8/23 10:14
  */
public class JavaPrimaryEngineer implements HardWork{

    @Override
    public void signIn() {
        System.out.println("java程序员签到");
    }

    @Override
    public void coding() {
        System.out.println("固资项目开发");
    }

    @Override
    public void knockOff() {
        System.out.println("打卡下班");
    }
}
