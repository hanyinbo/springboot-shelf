package com.aison.test.design.singleInstance;

public class HungrySingleInstance {

    private static final HungrySingleInstance instance = new HungrySingleInstance();
    private HungrySingleInstance(){
    }

    public static HungrySingleInstance getInstance(){
        return instance;
    }
    
    public static void main(String[] args) {
        for(int i=0;i<100;i++){
            new Thread(()->{
                System.out.println( HungrySingleInstance.getInstance().hashCode());
            }).start();

        }

    }
}
