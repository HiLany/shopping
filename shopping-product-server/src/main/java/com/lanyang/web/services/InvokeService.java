package com.lanyang.web.services;

import reactor.core.publisher.Mono;

/**
 * 通过webflux的webclient来访问restful API
 * Created by lany on 2018/11/5.
 */
public interface InvokeService {

    Mono<String> someRestCall(String name);


}
