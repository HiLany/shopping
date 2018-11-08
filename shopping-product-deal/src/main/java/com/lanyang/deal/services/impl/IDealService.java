package com.lanyang.deal.services.impl;

import com.lanyang.deal.services.DealService;
import com.lanyang.feignClientsForProductDeal.ProductServerFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单处理服务实现类
 * Created by lany on 2018/11/7.
 */
@Service
public class IDealService implements DealService{

    @Autowired
    private ProductServerFeignClient productServerFeignClient;

    @Override
    public String createOrder(String name) {
        Boolean flag = productServerFeignClient.SearchStorageByProduct(name);
        if(flag){
            return "hi, your order is created";
        }else
            return "Sorry ,this product is unsufficient";

    }
}
