package com.aison.test.basic;

import java.util.stream.Stream;

/**
 * 测试 jdk8 reduce
 *  将所有数据归纳到一个数据
 */
public class TestJava8Reduce {
    public static void main(String[] args) {
       Integer num = Stream.of(4,6,4,32,2).reduce(0,(x,y)->{
            System.out.println("x+y="+x+y);
            return x+y;

        });
        String nums = Stream.of("4","8","0","1","2").sorted().reduce("",(x,y)->{
            System.out.println("x+y="+x+y);
            return x+y;
        });
        //获取最大值
       Integer max =  Stream.of(4,6,4,32,2).reduce(0,(x,y)->{
            return x>y ? x:y;
        });
        System.out.println(num);
        System.out.println(nums);
        System.out.println(max);
    }
}
