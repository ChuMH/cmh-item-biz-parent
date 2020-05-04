package com.cmh.item.biz.service.impl.redis;

import com.cmh.item.biz.msg.CommRateLimiter;
import com.cmh.item.biz.msg.RateLimiterMode;
import com.cmh.item.biz.msg.RateLimiterStrategy;
import com.cmh.item.biz.sdk.service.redis.RedisService;
import com.cmh.project.basis.base.ResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * @author：
 * @data：
 * @description：
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private Jedis jedisClient;

    @CommRateLimiter(strategies = {@RateLimiterStrategy(mode = RateLimiterMode.COUNT_MODE,value = 2)})
    public ResultBuilder<Boolean> testLimit() {
        jedisClient.set("test","123");
        return ResultBuilder.success(true);
    }

    @CommRateLimiter(strategies = {@RateLimiterStrategy(mode = RateLimiterMode.RATE_MODE,value = 2)})
    public ResultBuilder<Boolean> testRate() {
        jedisClient.set("test","123");
        return ResultBuilder.success(true);
    }
}
