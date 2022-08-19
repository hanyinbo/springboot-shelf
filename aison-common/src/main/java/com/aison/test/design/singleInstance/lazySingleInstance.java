package com.aison.test.design.singleInstance;

public class lazySingleInstance {
    private static lazySingleInstance lazySingleInstance;
    public static lazySingleInstance getInstance() throws InterruptedException {
        if (lazySingleInstance == null){
            Thread.sleep(1);
            lazySingleInstance = new lazySingleInstance();
        }
        return lazySingleInstance;
    }
    public String getKey(String key){
        return key;
    }

    public static void main(String[] args) {
        for (int i=0 ;i<100;i++){
            new Thread(()-> {
                try {
                    System.out.println(lazySingleInstance.getInstance().hashCode());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }


//        SingInstance instance = SingInstance.getInstance();
//        String s = instance.getKey("加油");
//        System.out.println("单例获取："+s);
    }
}
