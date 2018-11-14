package com.lanyang.security;

import feign.Contract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;

/**
 * Created by lany on 2018/11/14.
 */
//@Configuration
public class ProductServerConfiguration {


    @Value("${client.username}")
    private String name;


    @Value("${client.password}")
    private String password;


    @Bean
    public Contract feignContract(){
        return new Contract.Default();
    }

    @Bean
    public BasicAuthorizationInterceptor basicAuthorizationInterceptor(){
        return new BasicAuthorizationInterceptor(name,password);
    }

}
