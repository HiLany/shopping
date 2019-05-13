package com.lanyang.dealDetail.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lanyang.dealList.domains.DealList;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * deal manifest
 * Created by lany on 2019/3/26.
 */
@Entity
@Table(name = "t_deal_detail")
@NamedQuery(name="DealDetail.findAll",query = "select d from DealDetail d")
public class DealDetail {

    @Id
    @GeneratedValue(generator = "myGen")
    @GenericGenerator(name = "myGen",strategy = "uuid")
    private String id;

    @Column(name = "detail_code")
    private String detailCode;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "num")
    private int num;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "deal_list_code")
    private DealList dealList;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    @Column(name = "last_update_time")
    private String lastUpdateTime;

    public DealDetail() {
    }

    public String getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public DealList getDealList() {
        return dealList;
    }

    public void setDealList(DealList dealList) {
        this.dealList = dealList;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return "DealDetail{" +
                "detailCode='" + detailCode + '\'' +
                ", productCode='" + productCode + '\'' +
                ", num=" + num +
                ", dealList=" + dealList +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }
}
