package com.lanyang.web.controlls;

import com.lanyang.web.services.InvokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by lany on 2018/11/2.
 */
@RestController
public class InvokeServiceProvider {

    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    private InvokeService invokeService;

    @RequestMapping(value = "invoke" ,method = RequestMethod.GET)
    public String invokeAPI(@RequestParam String name){
        return restTemplate.getForObject("http://shopping-product-description/hi?name="+name,String.class);
    }

    @RequestMapping(value = "reactive" ,method = RequestMethod.GET)
    public String reactiveInvoke(@RequestParam String name){
        return invokeService.someRestCall(name).toString();
    }

}
