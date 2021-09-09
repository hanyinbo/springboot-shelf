package com.aison.utils;

public class tes2 {
    public static void main(String[] args) {
       String id =  UniqId.getInstanceWithLog().getUniqID();

      long ids =  Long.valueOf( id).longValue();

        System.out.println("主键："+ids);
    }
}
