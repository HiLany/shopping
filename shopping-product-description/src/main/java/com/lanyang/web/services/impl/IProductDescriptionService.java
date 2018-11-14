package com.lanyang.web.services.impl;

import com.lanyang.feignClientsForProductDescription.ProductServerFeignClient;
import com.lanyang.web.services.ProductDescriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by lany on 2018/11/6.
 */
@Service
public class IProductDescriptionService implements ProductDescriptionService{

    private Logger logger = LoggerFactory.getLogger(IProductDescriptionService.class);

    @Value("${server.port}")
    private String port;

    @Autowired
    private ProductServerFeignClient productServerFeignClient;

    @Override
    public String SearchPort(){
        return "Hi, This Service Port is "+port;
    }

    @Override
    public String SearchByDescription(String name) {
        logger.info("SearchByDescription : {}" ,name);
        return productServerFeignClient.SearchDetailByProduct(name);
    }
}
