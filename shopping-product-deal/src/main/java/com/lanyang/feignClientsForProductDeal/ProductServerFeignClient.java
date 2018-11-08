package com.lanyang.feignClientsForProductDeal;

import com.lanyang.feignClientsForProductDeal.fallbacks.ProductServerFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * FeignClient -> 消费shopping-product-server服务
 * Created by lany on 2018/11/7.
 */
@FeignClient(value = "shopping-product-server",fallback = ProductServerFeignFallBack.class)
public interface ProductServerFeignClient {

    @RequestMapping(value = "/product/SearchStorageByProduct",method = RequestMethod.GET)
    Boolean SearchStorageByProduct(@RequestParam("productName")String productName);

}
