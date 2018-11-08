package com.lanyang.product.services;

/**
 * 商品管理服务接口
 * Created by lany on 2018/11/7.
 */
public interface ProductService {

    /**
     * 查看库存
     * @return
     */
    Boolean SearchStorageByProduct(String productName) throws InterruptedException;

    /**
     * 查看商品详细
     * @param productName
     * @return
     */
    String SearchDetailByProduct(String productName) throws InterruptedException;

}
