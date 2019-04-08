package com.lanyang.dealDetail.services;

import com.lanyang.dealDetail.domains.DealDetail;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单处理服务
 * Created by lany on 2018/11/7.
 */
public interface DealService {

    /**
     * 创建订单
     */
    String createOrder(String name);

    @Transactional
    DealDetail saveAndUpdateDealDetail(DealDetail dealDetail);

    @Transactional
    DealDetail addDealDetail(DealDetail dealDetail);

    @Transactional(readOnly = true)
    List<DealDetail> findAll();

    @Transactional
    Boolean deleteDealDetail(DealDetail dealDetail);

    @Transactional(readOnly = true)
    DealDetail findByDetailCode(String detailCode);

}
