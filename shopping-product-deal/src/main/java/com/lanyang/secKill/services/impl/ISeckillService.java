package com.lanyang.secKill.services.impl;

import com.lanyang.dealDetail.domains.DealDetail;
import com.lanyang.dealDetail.services.DealService;
import com.lanyang.dealList.domains.DealList;
import com.lanyang.dealList.services.DealListService;
import com.lanyang.secKill.services.SeckillService;
import com.lanyang.utils.RedisDistributedLock;
import com.shopping.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by lany on 2019/5/12.
 */
@Service
public class ISeckillService implements SeckillService {

    protected static final Logger logger = LoggerFactory.getLogger(ISeckillService.class);

    /**抢购限时时间**/
    private static long TIMEOUT = 3000L;
    /**商品库存**/
    private static int INVENTORY_PRODUCT = 10;
    /**锁过期时间**/
    private static long EXPIRE_TIME = 2000L;

    @Autowired
    private RedisDistributedLock redisDistributedLock;

    @Autowired
    private DealService dealService;

    @Autowired
    private DealListService dealListService;

    @Override
    @Transactional
    public String sickill(String productCode,String user) {
        long startTime = System.currentTimeMillis();
        //如果抢购时间还未结束，那么继续抢购
        while((startTime+TIMEOUT)>=System.currentTimeMillis()){
            //判断库存是否满足
            if(INVENTORY_PRODUCT <= 0){
                break;
            }
            String requestId = UUID.randomUUID().toString();
            // TODO: 2019/5/13 进行抢购
            if(redisDistributedLock.getRedisDistributedLock(productCode,requestId,EXPIRE_TIME)){
                //如果用户拿到了该商品的锁，那么就代表抢购成功，返回其用户名称，并将库存-1
                logger.info("用户 {} 抢到了锁....",user);
                try {
                    //判断库存是否满足
                    if(INVENTORY_PRODUCT <= 0){
                        break;
                    }
                    //生成订单动作
                    if(generateOrder(user,productCode)){
                        //如果定单成功生成
                        INVENTORY_PRODUCT -= 1;
                        logger.info("用户 {} 抢到了该商品！剩余库存 {}",user,INVENTORY_PRODUCT);
                        return user+":"+String.valueOf(INVENTORY_PRODUCT);
                    }
                } finally {
                    logger.info("用户 {} 释放锁....",user);
                    redisDistributedLock.releaseRedisDistributedLock(productCode,requestId);
                }
            }
        }

        return "";
    }

    /**
     * 生成订单动作
     * @param user 下单的用户
     * @param productCode  下单的商品
     * @return
     */
    private boolean generateOrder(String user,String productCode){
        try {
            //生成deallist
            DealList dealList = new DealList();
            dealList.setDealListCode("MS_NUM"+UUID.randomUUID().toString());
            dealList.setDealState("0");
            dealList.setLastUpdateTime(TimeUtils.getCurrentTime());
            dealList.setUserCode(user);
            dealList = dealListService.saveAndUpdateDealList(dealList);
            Set<DealDetail> dealDetails = new HashSet<>();

            //生成dealdetail
            DealDetail dealDetail = new DealDetail();
            dealDetail.setProductCode(productCode);
            dealDetail.setDealList(dealList);
            dealDetail.setLastUpdateTime(TimeUtils.getCurrentTime());
            dealDetail.setNum(1);
            dealDetails.add(dealDetail);
            dealService.addDealDetail(dealDetail);

            logger.info("用户{}下单成功，订单编号{}",dealList.getUserCode(),dealList.getDealListCode());
            return true;
        } catch (Exception e) {
            logger.info("生成订单错误");
            e.printStackTrace();
        }
        return false;
    }
}
