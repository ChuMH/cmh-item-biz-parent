package com.cmh.item.biz.sdk.service.redis;

import com.cmh.project.basis.base.ResultBuilder;

/**
 * @author：
 * @data：
 * @description：
 */
public interface RedisService {

     ResultBuilder<Boolean> testLimit();

     ResultBuilder<Boolean> testRate();
}
