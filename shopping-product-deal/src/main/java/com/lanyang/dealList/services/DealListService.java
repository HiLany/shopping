package com.lanyang.dealList.services;

import com.lanyang.dealList.domains.DealList;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lany on 2019/3/26.
 */
public interface DealListService {

    @Transactional
    DealList saveAndUpdateDealList(DealList dealList);

    @Transactional
    Boolean deleteDealList(DealList dealList);

    @Transactional(readOnly = true)
    List<DealList> findAll();

    @Transactional(readOnly = true)
    DealList findByDealListCode(String dealListCode);

}
