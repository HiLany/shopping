package com.lanyang.dealDetail.services.impl;

import com.lanyang.dealDetail.domains.DealDetail;
import com.lanyang.dealDetail.repositories.DealDetailRepository;
import com.lanyang.dealDetail.services.DealService;
import com.lanyang.feignClientsForProductDeal.ProductServerFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单处理服务实现类
 * Created by lany on 2018/11/7.
 */
@Service
public class IDealService implements DealService{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductServerFeignClient productServerFeignClient;

    @Autowired
    private DealDetailRepository dealDetailRepository;

    @Override
    public String createOrder(String name) {
        Boolean flag = productServerFeignClient.SearchStorageByProduct(name);
        if(flag){
            return "hi, your order is created";
        }else
            return "Sorry ,this product is unsufficient";

    }

    /**
     * 添加产品详细
     * 需要检查该产品的库存情况，判断是否有货。
     * @param dealDetail
     * @return
     */
    @Override
    public DealDetail addDealDetail(DealDetail dealDetail) {
        long product_num = productServerFeignClient.checkInventoryByProductCode(dealDetail.getProductCode());
        if(product_num <= 0){//judge inventory of product
            logger.error("The product's inventory is not satisfy!");
            return null;
        }
        return dealDetailRepository.save(dealDetail);
    }

    @Override
    public DealDetail saveAndUpdateDealDetail(DealDetail dealDetail) {

        if(dealDetail.getDetailCode().equals("") || dealDetail.getDetailCode() == null ){
            return null;
        }
        return dealDetailRepository.saveAndFlush(dealDetail);
    }

    @Override
    public List<DealDetail> findAll() {
        return dealDetailRepository.findAll();
    }

    @Override
    public Boolean deleteDealDetail(DealDetail dealDetail) {

        if(dealDetail.getDetailCode().equals("") || dealDetail.getDetailCode() == null ){
            return false;
        }

        DealDetail dealDetail_temp = dealDetailRepository.findByDetailCode(dealDetail.getDetailCode());

        if(dealDetail_temp == null){
            return false;
        }

        dealDetailRepository.delete(dealDetail_temp);
        return true;
    }

    @Override
    public DealDetail findByDetailCode(String detailCode) {
        return dealDetailRepository.findByDetailCode(detailCode);
    }
}
