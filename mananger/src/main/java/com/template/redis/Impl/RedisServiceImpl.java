package com.template.redis.Impl;


import com.template.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Title: RedisServiceImpl
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/7 15:37
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public void set(String key, Object value, long seconds) {
        redisTemplate.opsForValue().set(key,value,seconds, TimeUnit.SECONDS);
    }

    @Override
    public String getStr(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void setStr(String key, String value) {
        stringRedisTemplate.opsForValue().set(key,value);
    }

    @Override
    public void setStr(String key, String value, long seconds) {
        stringRedisTemplate.opsForValue().set(key,value,seconds,TimeUnit.SECONDS);
    }

    @Override
    public void increment(String key, long value, long seconds) {
        Boolean hasKey = stringRedisTemplate.hasKey(key);
        if(!hasKey){
            stringRedisTemplate.opsForValue().set(key, String.valueOf(value),seconds,TimeUnit.SECONDS);
        }else {
            stringRedisTemplate.opsForValue().increment(key,value);
        }
    }

    @Override
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }
}
