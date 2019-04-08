package com.lanyang.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lany on 2019/4/2.
 */
@Controller
public class UIController {

    @RequestMapping("/index")
    public String welcome(){
        return "index";
    }

}
