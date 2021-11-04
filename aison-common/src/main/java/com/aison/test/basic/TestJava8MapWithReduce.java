package com.aison.test.basic;

import io.swagger.models.auth.In;

import java.util.stream.Stream;

public class TestJava8MapWithReduce {
    public static void main(String[] args) {
     Integer num =  Stream.of(new Person("刘德华",19,"歌手"),
                new Person("爱德华",20,"理发师"),
                new Person("刘德华",19,"歌手"),
                new Person("王五",20,"理发师"),
                new Person("张三",20,"理发师")).map(person -> person.getAge()).reduce(0,(x,y)->{
                    return x+y;
        });

        Integer other =  Stream.of(new Person("刘德华",19,"歌手"),
                new Person("爱德华",20,"理发师"),
                new Person("刘德华",19,"歌手"),
                new Person("王五",20,"理发师"),
                new Person("张三",20,"理发师")).map(Person::getAge).reduce(0,Integer::sum);

       Integer max =  Stream.of(new Person("刘德华",19,"歌手"),
                new Person("爱德华",20,"理发师"),
                new Person("刘德华",19,"歌手"),
                new Person("王五",20,"理发师"),
                new Person("张三",20,"理发师")).map(Person::getAge).reduce(0,Math::max);

       Integer time = Stream.of("a","u","p","a","u","e").map(ch -> ch.equals("a")? 1:0).reduce(0,Integer :: sum);
        System.out.println(time);
        System.out.println(max);
        System.out.println(other);
        System.out.println(num);
    }
}
