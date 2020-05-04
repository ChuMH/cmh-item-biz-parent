package com.cmh.item.biz.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author：初明昊
 * @data：2020/04/05
 * @description：拦截器配置类
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${sso.exclude}")
    private String exclude;

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(exclude);
    }
}
