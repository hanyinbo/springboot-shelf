package com.aison.test.design.singleInstance;

public class SynchronizedSingleInstance {
    // 加volatile 防止在JIT即时编译时，产生线程安全问题
    private static volatile SynchronizedSingleInstance singleInstance;

    private SynchronizedSingleInstance(){

    }
    public static synchronized SynchronizedSingleInstance getInstance(){
        if(singleInstance==null){
            singleInstance = new SynchronizedSingleInstance();
        }
        return singleInstance;
    }

    public static void main(String[] args) {
        for (int i=0;i<100;i++){
            new Thread(()->{
                System.out.println(SynchronizedSingleInstance.getInstance().hashCode());
            }).start();
        }

    }
}
