package com.lanyang.web.services;

/**
 * Created by lany on 2018/11/6.
 */
public interface ProductDescriptionService {

    /**
     * 根据名称查询该产品描述
     * @param name
     * @return
     */
    String SearchByDescription(String name);

    /**
     * 用于测试zuul网关功能
     * @return
     */
    String SearchPort();

}
