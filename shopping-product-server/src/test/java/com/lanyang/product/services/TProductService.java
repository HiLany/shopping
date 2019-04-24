package com.lanyang.product.services;


import com.lanyang.product.domain.entity.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * Created by lany on 2019/3/29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TProductService {

    @Autowired
    ProductService productService;

//    @Test
    public void testAdd(){

        Product product = new Product();
        product.setProductCode("1010");
        product.setProductName("ChongQing");
        product.setInventory(5000);
        product.setLastUpdateTime(LocalDateTime.now().toString());
        product.setPicAddr("");
        product.setPrice(5000);


        Assert.assertEquals(product,productService.saveAndUpdateProduct(product));

    }

//    @Test
    public void testSearchByCondition(){
        Product product = new Product();
        product.setProductCode("1001");
        Page<Product> products = productService.findProductCriteria(1,5,product);
        System.out.println(products);
    }

//    @Test
    public void testSearch(){
        System.out.println(productService.findOne("f7e69dca-8725-4e8e-b3ea-4227d4515653"));
    }


}
