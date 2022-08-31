package com.aison.test.design.proxy.staticProxy;

/**
  * 代理类
  * @author hyb
　* @date 2022/8/30 11:46
  */
public class ProxyPlayer implements Gaming {
    //被代理的对象
    private GamePlayer gamePlayer;

    public ProxyPlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }


    @Override
    public Integer playing() {
        System.out.println("代理开始接单");
        Integer playing = gamePlayer.playing();
        Integer brokerage=playing+20;
        System.out.println("自己玩"+playing+"元，第三方代理佣金："+brokerage+"元");
        System.out.println("代理结束");
        return brokerage;
    }
}
