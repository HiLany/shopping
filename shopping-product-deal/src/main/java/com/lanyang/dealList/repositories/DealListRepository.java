package com.lanyang.dealList.repositories;

import com.lanyang.dealList.domains.DealList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lany on 2019/3/26.
 */
@Repository
public interface DealListRepository extends JpaRepository<DealList,String> {

    DealList findByDealListCode(String dealListCode);

}
