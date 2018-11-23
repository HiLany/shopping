package com.lanyang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * 默认情况下，@EnableDiscoveryClient默认将应用程序集成到远程服务注册中心。
 * 自动注册行为可以在该注解中手动关闭。@EnableDiscoveryClient(autoRegister=false)
 */
@SpringBootApplication
@EnableEurekaClient
//@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
//@EnableFeignClients
public class ShoppingProductServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingProductServerApplication.class, args);
	}
}
