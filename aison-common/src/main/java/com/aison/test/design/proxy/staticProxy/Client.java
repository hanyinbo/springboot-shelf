package com.aison.test.design.proxy.staticProxy;

public class Client {
    public static void main(String[] args) {
        GamePlayer gamePlayer = new GamePlayer();
        ProxyPlayer proxyPlayer = new ProxyPlayer(gamePlayer);
        proxyPlayer.playing();
    }
}
