package com.lanyang.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by lany on 2018/11/2.
 */
@Configuration
public class Consumer {

    /**
     * ribbon是一个负载均衡客户端，默认均衡了http以及ftp请求。
     * 默认情况下feign集成了ribbon客户端.
     * @return
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
