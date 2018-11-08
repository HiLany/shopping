package com.lanyang.deal.controllers;

import com.lanyang.deal.services.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单处理接口
 * Created by lany on 2018/11/7.
 */
@RestController
@RequestMapping("/deal")
public class DealController {

    @Autowired
    private DealService dealService;

    @RequestMapping(value = "/createOrder",method = RequestMethod.GET)
    public String createOrder(@RequestParam("name") String name){
        return dealService.createOrder(name);
    }

}
