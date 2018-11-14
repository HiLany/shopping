package com.lanyang.feignClientsForProductDescription;

import com.lanyang.feignClientsForProductDescription.fallBacks.ProductServerFeignFallBack;
import com.lanyang.security.ProductServerConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *  FeignClient -> 消费shopping-product-server服务
 * Created by lany on 2018/11/7.
 */
@FeignClient(value = "shopping-product-server",fallback = ProductServerFeignFallBack.class)
@Primary
public interface ProductServerFeignClient {

    @RequestMapping(value = "/product/SearchDetailByProduct", method = RequestMethod.GET)
    String SearchDetailByProduct(@RequestParam(value = "productName")String productName);

}
