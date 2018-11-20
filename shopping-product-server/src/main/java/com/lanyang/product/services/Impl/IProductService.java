package com.lanyang.product.services.Impl;

import com.lanyang.product.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 产品服务实现类
 * Created by lany on 2018/11/7.
 */
@Service
public class IProductService implements ProductService{

    private Logger logger = LoggerFactory.getLogger(IProductService.class);

    @Value("${deliver.messages:no messages}")
    private String messages;


    @Override
    public Boolean SearchStorageByProduct(String productName)  throws InterruptedException{
        logger.info("SearchStorageByProduct: {}",productName);
        boolean flag = false;
        try {
//            Thread.sleep(2000L);
            int nums = (int)(Math.random()*100);
            if(nums > 40){
                flag = true;
                logger.info("ProductName :"+productName +"is sufficient");
            }else
                logger.info("ProductName :"+productName +"is not enough");
        } catch (Exception e) {
            logger.info("Thread Error");
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public String SearchDetailByProduct(String productName) throws InterruptedException{
        Thread.sleep(2000L);
        return "ProductName :"+productName + " Category: Fruit , Description: 'so delicious!'\n"+messages;
    }

    /**
     * 随机生成休眠时间(ms)
     * @return
     */
    public long RandomNum(){
        int sleepTime = (int)(Math.random()*30+31)*100;
        logger.info("SleepTime : {}",sleepTime);
        return sleepTime;
    }

}
