//package com.lanyang.security;
//
//
//import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
///**
// * 配置该应用程序的访问权限
// * Created by lany on 2018/11/1.
// */
//@Configuration
//public class ActuatorSecurity extends WebSecurityConfigurerAdapter {
//
//    @Override
//    public void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests()
//                .anyRequest().permitAll();
//    }
//
//
///*    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests()
//                .anyRequest().hasRole("TEST_ROLES")
//                .and()
//                .httpBasic();
//    }*/
//
//}
