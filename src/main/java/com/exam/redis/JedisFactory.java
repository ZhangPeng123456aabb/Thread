package com.exam.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

public class JedisFactory {

    private JedisSentinelPool jedisSentinelPool;

    private JedisPool jedisPool;

    public JedisFactory (JedisSentinelPool jedisSentinelPool, JedisPool jedisPool) {
        this.jedisSentinelPool = jedisSentinelPool;
        this.jedisPool = jedisPool;
    }

    public Jedis resource () {
        if (jedisSentinelPool != null) {
            return jedisSentinelPool.getResource();
        } else if (jedisPool != null) {
            return jedisPool.getResource();
        } else {
            return null;
        }
    }
}
