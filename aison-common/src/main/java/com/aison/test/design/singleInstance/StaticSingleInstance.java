package com.aison.test.design.singleInstance;

public class StaticSingleInstance {

    private StaticSingleInstance(){}

    private static class StaticHodler{
        private final static StaticSingleInstance instance = new StaticSingleInstance();
    }
    public static StaticSingleInstance getInstance(){
        return StaticHodler.instance;
    }
    public static void main(String[] args) {
       for(int i=0;i<100;i++){
           new Thread(()->{
               System.out.println(StaticSingleInstance.getInstance().hashCode());
           }).start();
       }
    }
}
