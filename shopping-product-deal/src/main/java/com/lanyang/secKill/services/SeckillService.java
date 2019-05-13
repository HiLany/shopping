package com.lanyang.secKill.services;

/**
 * 秒杀服务接口
 * Created by lany on 2019/5/12.
 */
public interface SeckillService {

    /**
     * 抢单
     * @param user
     * @return
     */
    String sickill(String productCode,String user);

}
