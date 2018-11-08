package com.lanyang.web.controller;

import com.lanyang.web.services.ProductDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lany on 2018/11/1.
 */
@RestController
public class Search {

    @Autowired
    private ProductDescriptionService productDescriptionService;

    @RequestMapping(value = "hi",method = RequestMethod.GET)
    /*@HystrixCommand(fallbackMethod = "homeError")*/
    public String home(@RequestParam(value = "name",defaultValue = "lany") String name){
        return productDescriptionService.SearchByDescription(name);
    }

    public String homeError(String name){
        return "sorry, "+name+",an error raised!";
    }

    @RequestMapping(value = "/invokeFeignCommand",method = RequestMethod.GET)
    public String SearchDescriptionByProduct(@RequestParam("name") String name){
        return productDescriptionService.SearchByDescription(name);
    }

}
