package com.lanyang.product.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lany on 2019/4/8.
 */
@Controller
public class UIController {

    @RequestMapping("/")
    public String welCome(){
        return "index";
    }

}
