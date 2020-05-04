package com.cmh.item.biz.service.business;

import com.cmh.item.biz.msg.CommRateLimiter;
import com.cmh.item.biz.msg.RateLimiterMode;
import com.cmh.item.biz.msg.RateLimiterStrategy;
import com.cmh.project.basis.base.ResultBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author：初明昊
 * @data：2020/05/01
 * @description：限流四种方式服务类
 */
@Slf4j
@Service
public class BizRateLimitService{

    /**
     * 通过限制单台机器访问次数，一分钟内二次
     * @return
     */
    @CommRateLimiter(strategies = {@RateLimiterStrategy(mode = RateLimiterMode.COUNT_MODE,value = 2)})
    public ResultBuilder rateLimitByCount() {
        return ResultBuilder.success();
    }

    /**
     * 配置令牌桶令牌生成速率，2/秒
     * @return
     */
    @CommRateLimiter(strategies = {@RateLimiterStrategy(mode = RateLimiterMode.RATE_MODE,value = 2)})
    public ResultBuilder rateLimitByRateLimit() {
        return ResultBuilder.success();
    }

    /**
     * 按照ip限制访问次数，单个ip访问，限制在2秒钟2次
     * @return
     */
    @CommRateLimiter(strategies = {@RateLimiterStrategy(mode = RateLimiterMode.IP_MODE,value = 2)})
    public ResultBuilder rateLimitByIp(Map request) {
        return ResultBuilder.success();
    }

    /**
     * 通过获取用户pin限制
     * @return
     */
    @CommRateLimiter(strategies = {@RateLimiterStrategy(mode = RateLimiterMode.PIN_MODE,value = 2)})
    public ResultBuilder rateLimitByPin(Map requestMap) {
        return ResultBuilder.success();
    }
}
