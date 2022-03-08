package com.aison.test.basic;

import java.util.stream.Stream;

/**
 * 测试JDK8  map sort distinct
 */
public class TestJava8Map {
    public static void main(String[] args) {
        Stream.of("2","1","9","0","8","2","0").map(Integer::parseInt).distinct().sorted((o1, o2) -> o1-o2).forEach(System.out::println);
        //指定对象distinct
       Stream.of(new Person("刘德华",19,"歌手"),
               new Person("爱德华",20,"理发师"),
               new Person("刘德华",19,"歌手")).distinct().forEach(System.out::println);
    }


    public static String getFixAssetsValue(String appId, String appSecret, String timestamp, Integer tenantId) {
        return "AppId=" + appId + "&appSecret=" + appSecret + "&Timestamp=" + timestamp + "&TenantId=" + tenantId;
    }


}

