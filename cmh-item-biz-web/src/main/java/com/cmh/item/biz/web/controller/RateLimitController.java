package com.cmh.item.biz.web.controller;

import com.cmh.item.biz.sdk.service.redis.RateLimitService;
import com.cmh.project.basis.base.ResultBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author：初明昊
 * @data：2020/05/01
 * @description：流量限制展示
 */
@RestController
@RequestMapping("/rate/limit")
public class RateLimitController {

    @Resource
    private RateLimitService rateLimitService;

    /**
     * 通过请求次数限制流量
     * @return
     */
    @RequestMapping("/count")
    public ResultBuilder rateLimitByCount(){
        return rateLimitService.rateLimitByCount();
    }

    /**
     * 通过使用RateLimit组件，利用令牌桶算法实现限制
     */
    @RequestMapping("/rateLimit")
    public ResultBuilder rateLimitByRateLimit(){
        return rateLimitService.rateLimitByRateLimit();
    }

    /**
     * 通过ip限制流量
     */
    @RequestMapping("/ip")
    public ResultBuilder rateLimitByIp(){
        Map<String , String> request = new HashMap<>(1);
        request.put("ip","127.0.0.1");
        return rateLimitService.rateLimitByIp(request);
    }

    /**
     * 通过pin限制流量
     */
    @RequestMapping("/pin")
    public ResultBuilder rateLimitByPin(){
        Map<String , String> request = new HashMap<>(1);
        request.put("pin","chuminghao");
        return rateLimitService.rateLimitByPin(request);
    }

}
