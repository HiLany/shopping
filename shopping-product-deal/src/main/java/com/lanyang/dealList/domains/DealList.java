package com.lanyang.dealList.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lanyang.dealDetail.domains.DealDetail;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * deal waste book.
 * Created by lany on 2019/3/26.
 */
@Entity
@Table(name = "t_deal_list")
@NamedQuery(name="DealList.findAll",query = "select d from DealList d")
public class DealList {

    @Id
    @GeneratedValue(generator="myGen")
    @GenericGenerator(name="myGen" ,strategy="guid")
    @Column(name = "id")
    private String id;

    @Column(name = "deal_list_code")
    private String dealListCode;

    @Column(name = "deal_state")
    private String dealState;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    @Column(name = "last_update_time")
    private String lastUpdateTime;

    @Column(name = "user_code")
    private String userCode;

    @OneToMany(mappedBy="dealList",fetch= FetchType.LAZY)
    private Set<DealDetail> details = new HashSet<DealDetail>();

    public DealList() {
    }

    public String getDealListCode() {
        return dealListCode;
    }

    public void setDealListCode(String dealListCode) {
        this.dealListCode = dealListCode;
    }

    public String getDealState() {
        return dealState;
    }

    public void setDealState(String dealState) {
        this.dealState = dealState;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Set<DealDetail> getDetails() {
        return details;
    }

    public void setDetails(Set<DealDetail> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "DealList{" +
                "dealListCode='" + dealListCode + '\'' +
                ", dealState='" + dealState + '\'' +
                ", lastUpdateTime=" + lastUpdateTime +
                ", userCode='" + userCode + '\'' +
                ", details=" + details +
                '}';
    }
}
