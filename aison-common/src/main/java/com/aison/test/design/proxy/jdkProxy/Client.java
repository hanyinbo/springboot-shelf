package com.aison.test.design.proxy.jdkProxy;

public class Client {
    public static void main(String[] args) {

        Football football = new Football();
        ProxySport proxySport = new ProxySport(football);
        Sporting instance = (Sporting) proxySport.getInstance();
        String playing = instance.playing();
        System.out.println(playing+",我还想打篮球");
    }
}
