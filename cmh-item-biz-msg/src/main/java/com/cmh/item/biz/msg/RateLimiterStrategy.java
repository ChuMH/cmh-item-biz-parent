package com.cmh.item.biz.msg;


public @interface RateLimiterStrategy {

    /**
     * 限流策略
     * @return
     */
    RateLimiterMode mode() default RateLimiterMode.NO;

    /**
     * 值
     * @return
     */
    long value() default 0;
}
