package com.lanyang.utils;

import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 *
 * Redis获取锁和解锁工具类
 * Created by lany on 2019/5/10.
 */
public class RedisUtils {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;


    /**
     * 获取redis分布式锁
     * @param jedis
     * @param lockKey
     * @param requestId
     * @param expireTime
     * @return boolean
     */
    public static boolean tryDistributedLock(Jedis jedis,String lockKey,String requestId,int expireTime){
        String result = jedis.set(lockKey,requestId,SET_IF_NOT_EXIST,SET_WITH_EXPIRE_TIME,expireTime);
        if(LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    /**
     * 解锁
     * @param jedis
     * @param lockKey
     * @param requestId
     * @return boolean
     */
    public static boolean tryDistributedUnLock(Jedis jedis,String lockKey,String requestId){
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey),Collections.singletonList(requestId));

        if(RELEASE_SUCCESS.equals(result)){
            return true;
        }
        return false;
    }

}
