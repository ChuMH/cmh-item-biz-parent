package com.cmh.item.biz.msg;


public enum RateLimiterMode {

    /**
     * 无执行策略
     */
    NO,

    /**
     * 限制接口访问总数
     */
    COUNT_MODE,

    /**
     * 限制接口访问速度
     */
    RATE_MODE,

    /**
     * 限制IP访问量
     */
    IP_MODE,

    /**
     * 限制用户访问量
     */
    PIN_MODE,
    ;
}
