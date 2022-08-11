package com.aison.configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class CaptchaConfig {

    @Bean
    public DefaultKaptcha defaultKaptcha(){
        DefaultKaptcha dk = new DefaultKaptcha();
        Properties properties = new Properties();
//        // 图片边框
        properties.setProperty("kaptcha.border", "yes");
        // 边框颜色
        properties.setProperty("kaptcha.border.color", "217,217,217");
//        // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "64,169,255");
//        // 图片宽
        properties.setProperty("kaptcha.image.width", "110");
        // 图片高
        properties.setProperty("kaptcha.image.height", "40");
//        // 干扰线的颜色
        properties.setProperty("kaptcha.noise.color", "yellow");
//        // 背景颜色渐变，开始颜色
        properties.setProperty("kaptcha.background.clear.from", "white");
        //背景颜色渐变，结束颜色
        properties.setProperty("kaptcha.background.clear.to", "white");
//        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "38");
//        // session key
        properties.setProperty("kaptcha.session.key", "code");
//        // 验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
//        // 字体
        properties.put("kaptcha.textproducer.font.names","宋体,楷体,微软雅黑");
        // 字符间隔
        properties.put("kaptcha.textproducer.char.space", "4");
        Config config = new Config(properties);
        dk.setConfig(config);
        return dk;
    }


}
