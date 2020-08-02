package com.cmh.item.biz.web.controller;

import com.alibaba.fastjson.JSON;
import com.cmh.item.biz.sdk.service.redis.RedisService;
import com.cmh.project.basis.base.ResultBuilder;
import com.cmh.project.basis.utils.others.StringUtil;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.xml.transform.Result;
import java.util.*;

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

    /**
     * redis客户端请求s
     */
    @Resource
    private Jedis jedisClient;

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

    @RequestMapping("/set/key")
    public ResultBuilder<Boolean> setNullKey(){
        Set<String> str = new HashSet();
        str.add("zhngsan");
        str.add("zhngsan2");
        str.add("zhngsan3");
        jedisClient.set("test2",JSON.toJSONString(str));
        return ResultBuilder.success();
    }

    @RequestMapping("/get/key")
    public ResultBuilder<List> getNullKey(){
        String str = jedisClient.get("test2");
        List<String> listQuery = (List<String>) JSON.parse(str);
        if(!CollectionUtils.isEmpty(listQuery)){
            System.out.println("hello world");
        }
        return ResultBuilder.success(listQuery);
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
