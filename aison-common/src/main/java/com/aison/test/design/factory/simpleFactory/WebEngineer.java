package com.aison.test.design.factory.simpleFactory;

public  class WebEngineer  implements JobContent{
    public  String study(){
        return "学习java、angular、vue、springCloud";
    }

    public  String work(){
        return "负责全栈开发";
    }

    public  void enjoy(){
        System.out.println("我要去摸鱼");
    }
}
