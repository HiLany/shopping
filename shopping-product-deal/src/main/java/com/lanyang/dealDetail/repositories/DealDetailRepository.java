package com.lanyang.dealDetail.repositories;

import com.lanyang.dealDetail.domains.DealDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lany on 2019/3/26.
 */
@Repository
public interface DealDetailRepository extends JpaRepository<DealDetail,String> {


    DealDetail findByDetailCode(String detailCode);

}
