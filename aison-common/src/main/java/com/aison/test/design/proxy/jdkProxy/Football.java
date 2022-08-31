package com.aison.test.design.proxy.jdkProxy;
/**
  * 目标类
  * @author hyb
　* @date 2022/8/31 10:30
  */
public class Football implements Sporting{

    @Override
    public String playing() {
        System.out.println("踢足球");
        return "踢足球";
    }

}
