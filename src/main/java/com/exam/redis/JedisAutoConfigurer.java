package com.exam.redis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

@Configuration
@EnableConfigurationProperties({RedisProperties.class})
public class JedisAutoConfigurer {

    private final static Logger logger = LoggerFactory.getLogger(JedisAutoConfigurer.class);

    @Bean
    @ConditionalOnMissingBean(JedisSentinelPool.class)
    public JedisPool jedisPoolFactory(RedisProperties redisProperties) {
        logger.info("[JedisPool装配]-开始");
        RedisProperties.Pool pool = redisProperties.getJedis().getPool();
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(pool.getMaxIdle());
        jedisPoolConfig.setMaxTotal(pool.getMaxActive());
        jedisPoolConfig.setMaxWaitMillis(pool.getMaxWait().toMillis());
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        //        jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
        // 是否启用pool的jmx管理功能, 默认true
        //        jedisPoolConfig.setJmxEnabled(true);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,
            redisProperties.getHost(), redisProperties.getPort(),
            (int)redisProperties.getTimeout().toMillis(), redisProperties.getPassword());
        logger.info("[JedisPool装配]-完成");
        return jedisPool;
    }

    @Bean
    @ConditionalOnBean(JedisSentinelPool.class)
    public JedisPool jedisPoolFactory(JedisSentinelPool jedisSentinelPool, RedisProperties redisProperties) {
        logger.info("[JedisPool装配]-开始");
        RedisProperties.Pool pool = redisProperties.getJedis().getPool();
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(pool.getMaxIdle());
        jedisPoolConfig.setMaxTotal(pool.getMaxActive());
        jedisPoolConfig.setMaxWaitMillis(pool.getMaxWait().toMillis());

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, jedisSentinelPool.getCurrentHostMaster().getHost(), jedisSentinelPool.getCurrentHostMaster().getPort(),
            (int)redisProperties.getTimeout().toMillis(), redisProperties.getPassword());
        logger.info("[JedisPool装配]-完成");
        return jedisPool;
    }

    @Bean
    @ConditionalOnProperty("spring.redis.sentinel.nodes")
    public JedisSentinelPool jedisSentinelFactory(RedisProperties redisProperties) {
        Set<String> nodeSet = new HashSet<>();

        RedisProperties.Sentinel sentinel = redisProperties.getSentinel();
        //获取到节点信息
        List<String> nodeArray = redisProperties.getSentinel().getNodes();
        //判断是否为空
        if(nodeArray == null || nodeArray.size() == 0){
            logger.error("RedisSentinelConfiguration initialize error nodeArray is null");
            throw new RuntimeException("RedisSentinelConfiguration initialize error nodeArray is null");
        }
        //循环注入至Set中
        for(String node : nodeArray){
            logger.info("Read node : {}。" , node);
            nodeSet.add(node);
        }

        RedisProperties.Pool pool = redisProperties.getJedis().getPool();
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(pool.getMaxIdle());
        jedisPoolConfig.setMaxTotal(pool.getMaxActive());
        jedisPoolConfig.setMaxWaitMillis(pool.getMaxWait().toMillis());

        //创建连接池对象
        JedisSentinelPool jedisPool = new JedisSentinelPool(sentinel.getMaster() ,nodeSet ,jedisPoolConfig ,(int)redisProperties.getTimeout().toMillis() , redisProperties.getPassword());

        return jedisPool;
    }

    @Bean
    public JedisFactory jedisResourceFactory (@Autowired(required = false) JedisSentinelPool jedisSentinelPool, JedisPool jedisPool) {
        return new JedisFactory(jedisSentinelPool, jedisPool);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();

        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(template.getKeySerializer());
        template.setHashValueSerializer(template.getValueSerializer());

        return template;
    }

}