package com.lanyang.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lany on 2019/5/12.
 */
@Component
public class RedisDistributedLock {

    protected static final Logger logger = LoggerFactory.getLogger(RedisDistributedLock.class);

    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;
    private static final String SET_IF_NOT_EXIST = "NX"; //当key不存在时就加锁，如果存在就不做任何操作
    private static final String SET_WITH_EXPIRE_TIME = "PX"; //key过期时间单位设置为毫秒（EX：单位秒）


    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String,Object> redisTemplate;

    public static final String UNLOCK_LUA;

    /**
     * means: 先获取锁对应的value值，比较该值与requestId（加锁的requestid）是否相等。如果相等那么就删除锁。
     */
    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }

    /**
     * 获取分布式锁
     * @param localKey 锁
     * @param requestId 请求标识
     * @param expireTime 过期时间
     * @return
     */
    public boolean getRedisDistributedLock(String localKey,String requestId,long expireTime){
        try {
            RedisCallback<String> callback = new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    JedisCommands jedisCommands ;
                    Object object = redisConnection.getNativeConnection();
                    if(object instanceof JedisCommands){
                        jedisCommands = (JedisCommands) object;
                        return jedisCommands.set(localKey,requestId,SET_IF_NOT_EXIST,SET_WITH_EXPIRE_TIME,expireTime);
                    }
                    return null;
                }
            };
            String result = redisTemplate.execute(callback);
//        logger.info("Result:{}",result);
            if(result != null && LOCK_SUCCESS.equals(result)){
                return true;
            }
        } catch (Exception e) {
            logger.error("Get RedisDistributedLock Error",e);
        }
        return false;
    }

    /**
     *  释放分布式锁
     * @param localKey 锁
     * @param requestId 请求标识
     * @return
     */
    public boolean releaseRedisDistributedLock(String localKey,String requestId) {
        // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
        try {
            /*List<String> keys = new ArrayList<>();
            keys.add(localKey);
            List<String> requestIds = new ArrayList<>();
            requestIds.add(requestId);*/

            RedisCallback<Long> callback = new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    Object object = redisConnection.getNativeConnection();
                    /**
                     * 1.通过jedis.eval()方法与lua脚本释放localKey上的锁。eval（）方法将lua脚本传入到redis服务端执行，该方法可以保证原子性。
                     * 2.eval()如何保证原子性： redis中，eval()会将lua脚本当作一条命令去执行，而且只有当该命令执行完成之后,redis才能执行其他的操作。
                     * 3.redis 的模式分为单机模式和集群模式，虽然调用的方法一样，但是因为这两种模式没有共同的接口，所以只能分别实现
                     */
                    if (object instanceof JedisCluster) {
                        return (Long) ((JedisCluster) object).eval(UNLOCK_LUA, Collections.singletonList(localKey), Collections.singletonList(requestId));
//                        return (Long) ((JedisCluster) object).eval(UNLOCK_LUA, Collections.singletonList(localKey), requestIds);
                    } else if (object instanceof Jedis) {
                        return (Long) ((Jedis) object).eval(UNLOCK_LUA, Collections.singletonList(localKey), Collections.singletonList(requestId));
//                        return (Long) ((Jedis) object).eval(UNLOCK_LUA, keys, requestIds);
                    }
                    return 0L;
                }
            };
            Long result = redisTemplate.execute(callback);
            if (RELEASE_SUCCESS.equals(result)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Release RedisDistributedLock Error",e);
        }
        return false;
    }

}
