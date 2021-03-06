package com.lanyang.product.controllers;



import com.lanyang.product.domain.entity.Product;
import com.lanyang.product.services.ProductService;


import com.shopping.core.dto.PageQueryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lany on 2018/11/7.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@Api(value = "产品服务接口")
@RequestMapping("/product")
public class PorductController {

    private Logger logger = LoggerFactory.getLogger(PorductController.class);

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

    @GetMapping("/searchDetailByProduct")
    public String searchDetailByProduct(String productName){
        String result = null;
        try {
            result = productService.SearchDetailByProduct(productName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @ApiOperation(value = "根据类型获取产品列表",notes = "根据类型查询产品，并返回产品集合。")
    @ApiImplicitParam(name="productType",value = "产品种类编码",required = true,dataType = "String")
    @GetMapping("/searchListByProductType/{productType}")
    public List<Product> searchListByProductType(@PathVariable String productType){
        if(productType.equals("") || productType == null){
            return null;
        }
        return productService.searchListByProductType(Integer.valueOf(productType));
    }


    @ApiOperation(value = "获取产品库存数量",notes = "根据产品种类获取该产品库存数量")
    @ApiImplicitParam(name="productCode",paramType = "query",value = "产品编码",required = true,dataType = "String")
    @GetMapping("/checkInventoryByProductCode")
    public long checkInventoryByProductCode(@RequestParam(value = "productCode") String productCode){
        if(productCode.equals("") || productCode == null){
            logger.info("Product is Invalid!");
            return 0;
        }
        return productService.checkInventoryByProductCode(productCode);
    }

    @ApiOperation(value = "根据id查询产品",notes = "根据id查询产品Restful接口")
    @ApiImplicitParam(name="id",value = "产品id",required = true,dataType = "String")
    @GetMapping("/find/{id}")
    public Product findProduct(@PathVariable String id){
        return productService.findOne(id);
    }

    @ApiOperation(value = "添加产品信息",notes = "添加产品Restful接口")
    @ApiImplicitParam(name="product",value = "产品信息",required = true,dataType = "Product")
    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product){

        System.out.println(product);
        if(!checkProduct(product)){
            return null;
        }
        return productService.saveAndUpdateProduct(product);
    }

    @ApiOperation(value = "更新产品信息",notes = "更新产品Restful接口")
    @ApiImplicitParam(name="product",value = "产品信息",required = true,dataType = "Product")
    @PutMapping("/update")
    public Product updateProduct(@RequestBody Product product){
        if(!checkProduct(product)){
            return null;
        }
        return productService.saveAndUpdateProduct(product);
    }

    @ApiOperation(value = "删除产品信息",notes = "删除产品Restful接口")
    @ApiImplicitParam(name="id",value = "产品id",required = true,dataType = "String")
    @DeleteMapping("/delete/{id}")
    public boolean deleteProduct(@PathVariable String id){
        return productService.deleteProduct(id);
    }

    @ApiOperation(value = "根据条件查询产品信息",notes = "查询产品Restful接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageQueryDto",paramType = "body",value = "产品查询条件",required = true, dataType = "PageQueryDto")
    })
    @RequestMapping(value = "/findProductByCondition",method = RequestMethod.GET)
    public Page<Product> findProductByCondition(PageQueryDto<Product> pageQueryDto){
        logger.info(pageQueryDto.toString());
        Page<Product> productPage = null;
        try {

            productPage = productService.findProductByCondition(pageQueryDto);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return productPage;
        }
    }


    /**
     * check product
     * @param product
     * @return
     */
    private boolean checkProduct(Product product){
        if(product == null){
            return false;
        }
        if(product.getProductCode().equals("") || product.getProductCode() == null){
            return false;
        }
        return true;
    }



}
