package com.cmh.item.biz.msg.lock;


import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * @author：
 * @data：
 * @description：
 */
@Slf4j
public class RedisDistributeLock {
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    //加锁
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        if (LOCK_SUCCESS.equals(result)) {
            log.info("===>加锁成功！");
            return true;
        }
        return false;
    }

    private static final Long RELEASE_SUCCESS = 1L;

    //解锁
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }
}
