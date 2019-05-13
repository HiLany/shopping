package com.lanyang.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by lany on 2019/5/10.
 */
@Configuration
public class JedisConfig{

    protected static Logger logger = LoggerFactory.getLogger(JedisConfig.class);

    @Value("${redis.ip}")
    private String ip;

    @Value("${redis.maxActive}")
    private String maxActive;

    @Value("${redis.maxWait}")
    private String maxWait;

    @Value("${redis.maxIdel}")
    private String maxIdel;

    @Bean(name = "jedisPool")
    public JedisPool createJedisPool(){
        logger.info("init starting...");
        logger.info("{},{},{},{}",ip,maxActive,maxWait,maxIdel);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(Integer.parseInt(maxActive));
        jedisPoolConfig.setMaxWaitMillis(Long.parseLong(maxWait));
        jedisPoolConfig.setMaxIdle(Integer.parseInt(maxIdel));
        logger.info("init finished");
        return new JedisPool(jedisPoolConfig,ip);
    }

}
