package com.lanyang.configurations;

import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lany on 2018/11/14.
 */
//@Configuration
public class PatternServiceRouterConfigs {

    /**
     * ParrternServiceRouteMapper对象可以让开发者通过正则表达式来自定义路由映射的生成关系。
     * 其中构造函数的第一个参数是用来匹配服务名称是否符合该自定义规则的正则表达式
     * 第二个参数用来定义根据服务名中定义的内容转换出的路径表达式规则。
     * @return
     */
    @Bean
    public PatternServiceRouteMapper serviceRoutemapper(){
        return new PatternServiceRouteMapper(
                "(?<name>^.+)-(?<version>v.+$)",
                "${version}/${name}");
    }

}
