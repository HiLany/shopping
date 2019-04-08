package com.lanyang.dealList.services.impl;

import com.lanyang.dealList.domains.DealList;
import com.lanyang.dealList.repositories.DealListRepository;
import com.lanyang.dealList.services.DealListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lany on 2019/3/26.
 */
@Service
public class IDealListService implements DealListService{

    @Autowired
    private DealListRepository dealListRepository;

    @Override
    public DealList saveAndUpdateDealList(DealList dealList) {

        if(dealList.getDealListCode().equals("") || dealList.getDealListCode() == null){
            return null;
        }

        return dealListRepository.saveAndFlush(dealList);
    }

    @Override
    public Boolean deleteDealList(DealList dealList) {

        if(checkDealList(dealList)){
            return false;
        }

        DealList dealList_temp = dealListRepository.findByDealListCode(dealList.getDealListCode());

        if(dealList_temp == null){
            return false;
        }

        dealListRepository.findByDealListCode(dealList.getDealListCode());
        return true;
    }

    @Override
    public List<DealList> findAll() {
        return dealListRepository.findAll();
    }

    @Override
    public DealList findByDealListCode(String dealListCode) {
        return dealListRepository.findByDealListCode(dealListCode);
    }

    /**
     * verify
     * @param dealList
     * @return
     */
    private boolean checkDealList(DealList dealList){
        if(dealList.getDealListCode().equals("") || dealList.getDealListCode() == null){
            return false;
        }
        return true;
    }
}
