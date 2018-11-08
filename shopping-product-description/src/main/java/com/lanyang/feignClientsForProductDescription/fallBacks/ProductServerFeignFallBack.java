package com.lanyang.feignClientsForProductDescription.fallBacks;

import com.lanyang.feignClientsForProductDescription.ProductServerFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * feign回调类
 * Created by lany on 2018/11/7.
 */
@Component
public class ProductServerFeignFallBack implements ProductServerFeignClient{

    private Logger logger = LoggerFactory.getLogger(ProductServerFeignFallBack.class);

    @Override
    public String SearchDetailByProduct(String name) {
        logger.info("Remote Server is busy");
        return "Sorry, The Remote server is busy!";
    }
}
