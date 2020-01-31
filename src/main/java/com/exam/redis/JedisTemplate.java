package com.exam.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import static com.exam.redis.JedisConstants.ATOMIC_DEL_LUA_SCRIPT;
import static com.exam.redis.JedisConstants.OK;
import static com.exam.redis.JedisConstants.OK_LONG;

/**
 * redis常用操作
 */
@Component
public class JedisTemplate {
    @Autowired
    private JedisFactory jedisFactory;

    /**
     * 设置key->value，只有当前redis中不存在对应的key才会设置成功
     *
     * @param key
     * @param value
     * @param expireMills，超时时间，单位毫秒，如果为null表示永不过期
     * @return
     */
    public boolean setIfNotExist(String key, String value, Long expireMills) {
        SetParams params = new SetParams().nx();
        return set(key, value, params, expireMills);
    }

    /**
     * 设置key->value，只有当前redis中存在对应的key才会设置成功
     *
     * @param key
     * @param value
     * @param expireMills
     * @return expireMills，过期时间，单位毫秒，如果为null表示永不过期
     */
    public boolean setIfExist(String key, String value, Long expireMills) {
        SetParams params = new SetParams().xx();
        return set(key, value, params, expireMills);
    }

    /**
     * 设置key->value
     *
     * @param key
     * @param value
     * @param expireMills
     * @return expireMills，过期时间，单位毫秒，如果为null表示永不过期
     */
    public boolean set(String key, String value, Long expireMills) {
        SetParams params = new SetParams().px(expireMills);
        return set(key, value, params, expireMills);
    }

    /**
     * 删除某个key
     *
     * @param key
     * @return 是否执行了删除操作
     */
    public boolean del(String key) {
        Jedis jedis = jedisFactory.resource();
        try {
            return OK_LONG.equals(jedis.del(key));
        } finally {
            jedis.close();
        }
    }

    /**
     * 如果当前存在key且key对应的value值和target相同，则删除，原子操作
     *
     * @param key
     * @param target
     * @return 是否执行了删除操作
     */
    public boolean del(String key, String target) {
        Jedis jedis = jedisFactory.resource();
        try {
            Object response = jedis.eval(ATOMIC_DEL_LUA_SCRIPT, 1, key, target);
            return response != null && response.equals(OK_LONG);
        } finally {
            jedis.close();
        }
    }

    /**
     * 查询key对应的value
     *
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = jedisFactory.resource();
        try {
            return jedis.get(key);
        } finally {
            jedis.close();
        }
    }

    private boolean set(String key, String value, SetParams setParams, Long expireMills) {
        Jedis jedis = jedisFactory.resource();
        if (expireMills != null) {
            setParams.px(expireMills);
        }
        try {
            String result = jedis.set(key, value, setParams);
            if (OK.equals(result)) {
                return true;
            }
            return false;
        } finally {
            jedis.close();
        }

    }
}
