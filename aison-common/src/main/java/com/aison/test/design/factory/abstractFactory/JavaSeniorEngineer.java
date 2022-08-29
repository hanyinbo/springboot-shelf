package com.aison.test.design.factory.abstractFactory;

/**
  * 接口实现类
  * @author hyb
　* @date 2022/8/23 10:14
  */
public class JavaSeniorEngineer implements Advising {

    @Override
    public void writeDoc() {
        System.out.println("编写需求文档");
    }

    @Override
    public void manageProject() {
        System.out.println("项目分工");
    }

    @Override
    public void meeting() {
        System.out.println("组织开发会议");
    }

    @Override
    public void knockOff() {
        System.out.println("延迟打卡下班");
    }
}
