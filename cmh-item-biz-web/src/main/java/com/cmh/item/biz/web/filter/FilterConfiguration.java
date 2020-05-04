package com.cmh.item.biz.web.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author：初明昊
 * @data：2020/4/15
 * @description：拦截器配置类
 */
@Configuration
public class FilterConfiguration {

    @Bean
    @Order(2)
    public FilterRegistrationBean filter(){
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new ProjectFilter());
        filter.addUrlPatterns("/*");
        filter.setName("sessionFilter");
        return filter;
    }

}
