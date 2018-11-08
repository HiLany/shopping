package com.lanyang.product.controllers;

import com.lanyang.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lany on 2018/11/7.
 */
@RestController()
@RequestMapping("/product")
public class PorductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/SearchStorageByProduct")
    public Boolean SearchStorageByProduct(String productName) {
        Boolean result = false;
        try {
            result = productService.SearchStorageByProduct(productName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/SearchDetailByProduct")
    public String SearchDetailByProduct(String productName){
        String result = null;
        try {
            result = productService.SearchDetailByProduct(productName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
