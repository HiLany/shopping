package com.lanyang.web.services.impl;

import com.lanyang.web.services.ProductDescriptionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by lany on 2018/11/6.
 */
@Service
public class IProductDescriptionService implements ProductDescriptionService{

    @Value("${server.port}")
    private String port;

    @Override
    public String SearchByDescription(String name) {
        return "hi " + name +", i am from port: " + port;
    }
}
