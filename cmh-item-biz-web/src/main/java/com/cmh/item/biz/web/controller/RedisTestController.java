package com.cmh.item.biz.web.controller;

import com.cmh.item.biz.sdk.service.redis.RedisService;
import com.cmh.project.basis.base.ResultBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author：初明昊
 * @data：2020/05/01
 * @description：缓存测试调通类
 */
@Slf4j
@RequestMapping("/redis")
@RestController
public class RedisTestController {

    @Resource
    private RedisService redisService;

    @RequestMapping("/limit/count")
    public ResultBuilder<Boolean> limitCount(){
        ResultBuilder<Boolean> res = redisService.testLimit();
        return res;
    }

    @RequestMapping("/limit/rate")
    public ResultBuilder<Boolean> limitRate(){
        ResultBuilder<Boolean> res = redisService.testRate();
        return res;
    }

    /*public boolean setnx(String key, String val) {
               Jedis jedis = null;
                 try {
                         jedis = jedisPool.getResource();
                        if (jedis == null) {
                                 return false;
                            }
                         return jedis.set(key, val, "NX", "PX", 1000 * 60).
                                equalsIgnoreCase("ok");
                     } catch (Exception ex) {
                     } finally {
                         if (jedis != null) {
                               jedis.close();
                             }
                     }
                 return false;
             }*/
}
