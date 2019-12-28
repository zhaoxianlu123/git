package com.example.demo1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class SpringbootRedisApplicationTests {


    @Autowired
    RedisConfig redisConfig;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void testRedis() {
        StringRedisTemplate redisTemplate = (StringRedisTemplate) redisConfig.getRedisTemplate();
        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        stringValueOperations.set("name", "forezp");
        System.out.println(stringValueOperations.get("name"));
    }

}
