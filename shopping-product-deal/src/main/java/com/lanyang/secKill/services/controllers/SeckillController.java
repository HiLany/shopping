package com.lanyang.secKill.services.controllers;

import com.lanyang.secKill.services.SeckillService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * Created by lany on 2019/5/13.
 */
@RestController
public class SeckillController {

    private static final String PRODUCT_CODE = "Product_ONE";

    @Autowired
    private SeckillService seckillService;

    /**
     * 模拟用户秒杀商品
     * @return
     */
    @GetMapping("/simulationSeckill")
    public List<String> simulationSeckill(){
        List<String> userList = new ArrayList<>();
        IntStream.range(0,10000).parallel().forEach(new IntConsumer() {
            @Override
            public void accept(int value) {
                userList.add("lanyang"+value);
            }
        });

        List<String> luckeyUsers = new ArrayList<>();
        userList.parallelStream().forEach(new Consumer<String>() {
            @Override
            public void accept(String user) {
                String result = seckillService.sickill(PRODUCT_CODE,user);
                if(StringUtils.isNotEmpty(result)&&!"".equals(result)){
                    luckeyUsers.add(result);
                }
            }
        });
        return luckeyUsers;
    }

}
