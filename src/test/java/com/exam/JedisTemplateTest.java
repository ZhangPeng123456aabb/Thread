package com.exam;

import java.util.UUID;

import com.exam.redis.JedisTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JedisTemplateTest {
    @Autowired
    private JedisTemplate jedisTemplate;

    @Test
    public void testJedis() {
        String key = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        String value = "hello world";
        Long expire = 3000L;
        assertTrue(null == jedisTemplate.get(key));

        assertTrue(jedisTemplate.set(key, value, expire));
        assertTrue(value.equals(jedisTemplate.get(key)));

        // 已经存在则setIfNotExist失败
        assertFalse(jedisTemplate.setIfNotExist(key, value, expire));

        try {
            // sleep，等待key过期
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(jedisTemplate.setIfNotExist(key, value, expire));
        value = value + "1";
        // 已经存在则setIfExist成功
        assertTrue(jedisTemplate.setIfExist(key, value, expire));
        assertTrue(value.equals(jedisTemplate.get(key)));

        // 带条件删除，条件不满足
        assertFalse(jedisTemplate.del(key, value + "2"));
        assertTrue(value.equals(jedisTemplate.get(key)));

        // 不带条件删除
        assertTrue(jedisTemplate.del(key));
        assertTrue(null == jedisTemplate.get(key));

        assertTrue(jedisTemplate.setIfNotExist(key, value, expire));
        // 带条件删除，条件满足
        assertTrue(jedisTemplate.del(key, value));
        assertTrue(null == jedisTemplate.get(key));
    }

}
