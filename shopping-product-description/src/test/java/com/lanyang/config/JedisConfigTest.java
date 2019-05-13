package com.lanyang.config;

import com.lanyang.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lany on 2019/5/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JedisConfigTest {

    protected static final Logger logger = LoggerFactory.getLogger(JedisConfigTest.class);

    @Autowired
    @Qualifier("jedisPool")
    private JedisPool jedisPool;

    private static int count = 20;

    @Test
    public void testLock(){

        //模拟并发访问
        CountDownLatch latch=new CountDownLatch(1);

        for(int i=0;i<10;i++){
            String requestId = UUID.randomUUID().toString();
            Thread thread = new Thread(){
                @Override
                public void run() {
                    Jedis jedis = null;
                    try {
                        latch.await();
                        jedis = jedisPool.getResource();
                        boolean result = RedisUtils.tryDistributedLock(jedis,"resource",requestId,2000);

                        if(result){
                            logger.info("{}-{},get Lock",Thread.currentThread().getName(),requestId);
                            System.out.println(--count);
                        }

                        RedisUtils.tryDistributedUnLock(jedis,"name",requestId);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        if(jedis != null){
                            jedis.close();
                        }
                    }

                }


            };

            thread.start();

        }

        latch.countDown();


    }



}
