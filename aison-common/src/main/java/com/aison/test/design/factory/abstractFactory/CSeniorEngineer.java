package com.aison.test.design.factory.abstractFactory;
/**
  * 接口实现类
  * @author hyb
　* @date 2022/8/26 16:29
  */
public class CSeniorEngineer implements Advising{
    @Override
    public void writeDoc() {
        System.out.println("c编写项目文档");
    }

    @Override
    public void manageProject() {
        System.out.println("C管理项目");
    }

    @Override
    public void meeting() {
        System.out.println("组织开发会议");
    }

    @Override
    public void knockOff() {
        System.out.println("提前打卡下班");
    }
}
