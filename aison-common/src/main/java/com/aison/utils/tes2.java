package com.aison.utils;

import java.util.ArrayList;
import java.util.List;

public class tes2 {
    public static void main(String[] args) {
//        String id = UniqId.getInstanceWithLog().getUniqID();
//        System.out.println("UniqId主键：" + id);
//        SnowFlakeID snowFlakeID = new SnowFlakeID();
//        Long sid = snowFlakeID.nextId();
//        Integer size = sid.toString().length();
//        System.out.println("雪花ID的size:"+size);
//        System.out.println("雪花算法生成的ID:" + snowFlakeID.nextId());
       final List<String> tests = new ArrayList<>();
       for(int i=0 ;i<10;i++){
           tests.add("测试final;"+i);
       }
        System.out.println(tests);
    }
}
