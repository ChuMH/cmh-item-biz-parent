package com.cmh.item.biz.service.impl.redis;

import com.cmh.item.biz.sdk.service.redis.RateLimitService;
import com.cmh.item.biz.service.business.BizRateLimitService;
import com.cmh.project.basis.base.ResultBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author：初明昊
 * @data：2020/05/01
 * @description：通过此服务实现，展示限流四种方式效果
 */
@Slf4j
@Service
public class RateLimitServiceImpl implements RateLimitService {

    @Autowired
    private BizRateLimitService bizRateLimitService;

    @Override
    public ResultBuilder rateLimitByCount() {
        return bizRateLimitService.rateLimitByCount();
    }

    @Override
    public ResultBuilder rateLimitByRateLimit() {
        return bizRateLimitService.rateLimitByRateLimit();
    }

    @Override
    public ResultBuilder rateLimitByIp(Map request) {
        return bizRateLimitService.rateLimitByIp(request);
    }

    @Override
    public ResultBuilder rateLimitByPin(Map request) {
        return bizRateLimitService.rateLimitByPin(request);
    }
}
