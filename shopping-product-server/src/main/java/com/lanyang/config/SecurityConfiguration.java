package com.lanyang.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by lany on 2018/11/14.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests()
//                .anyRequest().permitAll();
        /**
         * 如果访问http://localhost:8764/actuator/bus-refresh 出现403错误，就将安全验证给关掉
         *
         * resolve method : https://github.com/spring-cloud/spring-cloud-config/issues/950#issuecomment-377180581
         */
        httpSecurity.csrf().disable();
    }


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests()
//                .anyRequest().hasRole("TEST_ROLES")
//                .and()
//                .httpBasic();
//    }



}
