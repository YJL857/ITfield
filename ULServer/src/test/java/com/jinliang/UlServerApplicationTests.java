package com.jinliang;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest()
class UlServerApplicationTests {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
    }

    /**
     * redis 操作字符串
     */
    @Test
    void testString() {
        stringRedisTemplate.opsForValue().set("name", "叶金亮",20L, TimeUnit.SECONDS);
    }

    @Test
    void testGetString() {
        String name = stringRedisTemplate.opsForValue().get("name");
        System.out.println(name);
    }

}
