package com.aison.test.design.factory.abstractFactory;

/**
  * 接口实现类
  * @author hyb
　* @date 2022/8/23 10:12
  */
public class CPrimaryEngineer implements HardWork {

    @Override
    public void signIn() {
        System.out.println("C程序员签到");
    }

    @Override
    public void coding() {
        System.out.println("编写客户端代码");
    }

    @Override
    public void knockOff() {
        System.out.println("打卡下班");
    }
}
