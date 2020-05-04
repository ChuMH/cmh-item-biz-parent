package com.cmh.item.biz.msg.aop;

import com.cmh.item.biz.msg.CacheKey;
import com.cmh.item.biz.msg.Cacheable;


public class TestController {

    @Cacheable(key = "|1111111|",keyMode = Cacheable.KeyMode.CACHEKEY)
    public String hello(@CacheKey String str, @CacheKey  Integer age){
        System.out.println("hello");
        return "HelloWorld";
    }
}
