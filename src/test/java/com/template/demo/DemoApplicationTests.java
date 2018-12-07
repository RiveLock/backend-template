package com.template.demo;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    private Logger logger = LoggerFactory.getLogger(DemoApplicationTests.class);


    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void redisTest() {
        //redisTemplate.opsForValue().set("qq", "123");
        stringRedisTemplate.opsForValue().set("qq","123");
        String key = stringRedisTemplate.opsForValue().get("qq");
        logger.info(key);
        //System.out.println(key);
    }


    @Test
    public void contextLoads() {
    }

    @Test
    public void test1(){
        String name = "lzz";
        String password = "123456";
        logger.debug("debug...");
        logger.info("name:"+name+" password:"+password);
        logger.info("name:{},password:{}",name,password);
        logger.error("error...");
        logger.warn("warn....");
    }


}
