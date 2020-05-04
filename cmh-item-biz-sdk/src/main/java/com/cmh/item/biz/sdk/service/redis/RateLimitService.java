package com.cmh.item.biz.sdk.service.redis;

import com.cmh.project.basis.base.ResultBuilder;

import java.util.Map;

/**
 * @author：初明昊
 * @data：2020/05/01
 * @description：流向限制服务接口，用到redis但不限于redis
 */
public interface RateLimitService {

    ResultBuilder rateLimitByCount();

    ResultBuilder rateLimitByRateLimit();

    ResultBuilder rateLimitByIp(Map request);

    ResultBuilder rateLimitByPin(Map request);
}
