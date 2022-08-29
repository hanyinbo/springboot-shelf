package com.aison.test.design.factory.abstractFactory;
/**
  * 客户端调用
  * @author hyb
　* @date 2022/8/26 17:40
  */
public class Client {
    public static void main(String[] args) {
        IProductFactory javaFactory = new JavaEngineerFactory();
        Advising javaAdvising = javaFactory.createAdvising();
        javaAdvising.writeDoc();
        javaAdvising.manageProject();
        javaAdvising.meeting();
        javaAdvising.knockOff();

        HardWork javaWork = javaFactory.createWork();
        javaWork.signIn();
        javaWork.coding();
        javaWork.knockOff();

        System.out.println("========JAVA工程师========");

        CEngineerFactory cFactory = new CEngineerFactory();
        Advising cAdvising = cFactory.createAdvising();
        cAdvising.writeDoc();
        cAdvising.manageProject();
        cAdvising.meeting();
        cAdvising.knockOff();

        HardWork cWork = cFactory.createWork();
        cWork.signIn();
        cWork.coding();
        cWork.knockOff();

        System.out.println("========C工程师========");

    }
}
