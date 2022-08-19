package com.aison.test.design.singleInstance;

public class DoubleLockSingleInstance {
    // volatile关键字用于防止指令重排序
    private static volatile DoubleLockSingleInstance lockSingleInstance ;
    private DoubleLockSingleInstance(){}

    private static DoubleLockSingleInstance getInstance(){
        if(lockSingleInstance == null){
            synchronized (DoubleLockSingleInstance.class){
                if(lockSingleInstance == null){
                    lockSingleInstance = new DoubleLockSingleInstance();
                }
            }
        }
        return lockSingleInstance;
    }

    public static void main(String[] args) {
        for (int i=0;i<100;i++){
            new Thread(()->{
                System.out.println(DoubleLockSingleInstance.getInstance().hashCode());
            }).start();
        }
    }
}
