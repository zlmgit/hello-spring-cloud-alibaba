package com.zlm.hello.spring.cloud.alibaba.nacos.provider.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
@Component
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void put(String key , String value , long seconds){
        redisTemplate.opsForValue().set(key , value ,seconds, TimeUnit.SECONDS);
    }

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public Long getExpireByKey(String key){
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return expire;
    }

    public Long increment(String key){
        Long increment = redisTemplate.opsForValue().increment(key, 1);
        return increment;
    }
}
