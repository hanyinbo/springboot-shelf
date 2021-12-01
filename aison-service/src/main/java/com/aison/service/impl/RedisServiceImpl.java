package com.aison.service.impl;

import com.aison.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {
    @Value("${default.redis.key}")
    String defaultPrefix;

    private  RedisTemplate<String, Object> redisTemplate;


    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(defaultPrefix + key, value);
    }

    @Override
    public void setExpire(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(defaultPrefix + key, value, timeout, TimeUnit.SECONDS);
    }

    @Override
    public void setExpire(String key, long timeout) {
        redisTemplate.expire(defaultPrefix + key, timeout, TimeUnit.SECONDS);
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(defaultPrefix + key);
    }
}
