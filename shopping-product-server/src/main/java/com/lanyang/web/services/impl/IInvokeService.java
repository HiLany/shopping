package com.lanyang.web.services.impl;

import com.lanyang.web.services.InvokeService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * InvokeService 实现类
 * Created by lany on 2018/11/5.
 */
@Service
public class IInvokeService implements InvokeService{

    private final WebClient webClient;

    public IInvokeService (WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("http://shopping-product-description").build();
    }

    public Mono<String> someRestCall(String name){
        return this.webClient.get().uri("/hi?name={name}",name)
                .retrieve().bodyToMono(String.class);
    }

}
