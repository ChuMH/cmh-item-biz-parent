package com.cmh.item.biz.msg.aop;


import com.cmh.item.biz.msg.CommRateLimiter;
import com.cmh.item.biz.msg.RateLimiterMode;
import com.cmh.item.biz.msg.RateLimiterStrategy;
import com.cmh.project.basis.base.ResultBuilder;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.cmh.item.biz.msg.MethodUtils.geMethodAnnotation;
import static com.cmh.item.biz.msg.MethodUtils.getMethodFromTarget;


@Aspect
@Component
@Slf4j
public class RateLimiterAspect implements ApplicationListener<ContextRefreshedEvent> {

    @Pointcut("@annotation(com.cmh.item.biz.msg.CommRateLimiter)")
    public void rateLimiterAspect() {
    }

    /**
     * 单位时间的指定方法访问总数次数统计
     */
    private static Cache<String, AtomicInteger> maxReqManagement = CacheBuilder
            .newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    /**
     * 限制单位时间请求速率
     */
    private static Map<String, RateLimiter> rateLimiterManagement = new ConcurrentHashMap<>();

    /**
     * lua脚本上传，redis客户端返回的验证码
     */
    private volatile static String sha;


    /**
     * redis客户端请求s
     */
    @Resource
    private Jedis jedisClient;


    /**
     * 统计限流,根据配置的限流策略执行
     *
     * @param joinPoint 连接点
     * @return
     * @throws Throwable
     */
    @Around(value = "rateLimiterAspect()")
    public Object proceed(ProceedingJoinPoint joinPoint) throws Throwable {
        CommRateLimiter commRateLimiter = (CommRateLimiter) geMethodAnnotation(joinPoint, CommRateLimiter.class);
        RateLimiterStrategy[] strategies      = commRateLimiter.strategies();
        if (strategies == null || strategies.length <= 0) {
            return joinPoint.proceed();
        }
        final Method method            = getMethodFromTarget(joinPoint);
        String       executeMethodName = method.getName();
        for (RateLimiterStrategy strategy : strategies) {
            RateLimiterMode mode  = strategy.mode();
            long            limit = strategy.value();
            if (limit <= 0) {
                continue;
            }
            switch (mode) {
                case NO:
                    break;
                case COUNT_MODE: // 限制单位时间请求总数
                    if (!countMode(executeMethodName, limit)) {
                        return ResultBuilder.failure("请求次数达到上限");
                    }
                    break;
                case RATE_MODE: // 限制接口请求速度
                    if (!rateMode(executeMethodName, limit)) {
                        return ResultBuilder.failure("请求速率达到峰值");
                    }
                    break;
                case IP_MODE: //  根据IP进行限制 需要借助LUA脚本实现
                    if (!ipMode(executeMethodName, joinPoint, Long.toString(limit))) {
                        return ResultBuilder.failure("您的ip访问次数达到上限");
                    }
                    break;
                case PIN_MODE: // 根据PIN进行限制
                    if (!pinMode(executeMethodName, joinPoint, Long.toString(limit))) {
                        return ResultBuilder.failure("pin访问达到峰值");
                    }
                    break;
                default:
                    break;
            }
        }
        return joinPoint.proceed();
    }


    /**
     * 限制ip策略
     *
     * @param methodName
     * @param joinPoint
     * @param limit
     * @return
     */
    private boolean ipMode(String methodName, JoinPoint joinPoint, String limit) {
        try {
            String        ip = getParameter(joinPoint, "ip");
            if(!StringUtils.isNotBlank(ip)){
                return true;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("RATE_LIMITER_IP_");
            sb.append(ip).append(":").append(methodName);
            return (long) jedisClient.eval(luaScript, Lists.newArrayList(sb.toString()), Lists.newArrayList(limit)) == 1;
        } catch (Exception e) {
            log.error("===>RateLimiterAspect#ipMode,e", e);
        }
        return true;
    }

    /**
     * 限制pin策略
     *
     * @param methodName 方法名
     * @param joinPoint
     * @param limit
     * @return
     */
    private boolean pinMode(String methodName, JoinPoint joinPoint, String limit) {
        try {
            StringBuilder sb  = new StringBuilder();
            String        pin = getParameter(joinPoint, "pin");
            if(!StringUtils.isNotBlank(pin)){
                return true;
            }
            sb.append("RATE_LIMITER_PIN_");
            sb.append(pin).append(":").append(methodName);
            return (long) jedisClient.eval(luaScript, Lists.newArrayList(sb.toString()), Lists.newArrayList(limit)) == 1;
        } catch (Exception e) {
            log.error("===>RateLimiterAspect#pinMode,e", e);
        }
        return true;
    }

    /**
     * 总数
     *
     * @param methodName 方法名
     * @param limit      上限值
     * @return
     */
    private boolean countMode(String methodName, long limit) {
        try {
            AtomicInteger count = maxReqManagement.get(methodName, () -> new AtomicInteger(0));
            if (count.get() > limit) {
                return false;
            }else{
                count.incrementAndGet();
            }
        } catch (Exception e) {
            log.error("===>RateLimiterAspect#countMode,e", e);
        }
        return true;
    }

    /**
     * 获取入参
     *
     * @param joinPoint
     * @param parameterName
     * @return
     */
    private String getParameter(JoinPoint joinPoint, String parameterName) {
        Object[] args = joinPoint.getArgs();
        if (null == args || args.length < 0) {
            return "";
        }
        Map<String, String> arg = null;
        for (Object param : args) {
            if (param instanceof Map) {
                arg = (Map<String, String>) param;
                break;
            }
        }
        if (arg == null || arg.size() <= 0) {
            return "";
        }
        String parameter = arg.get(parameterName);
        return parameter;
    }

    /**
     * 速率
     *
     * @param methodName 方法名
     * @param limit      上限值
     * @return
     */
    private boolean rateMode(String methodName, long limit) {
        try {
            if (!rateLimiterManagement.containsKey(methodName)) {
                rateLimiterManagement.putIfAbsent(methodName,
                        RateLimiter.create(limit, 1000, TimeUnit.MILLISECONDS));
            }
            RateLimiter rateLimiter = rateLimiterManagement.get(methodName);
            // 获取到令牌
            if (!rateLimiter.tryAcquire()) {
                return false;
            }
        } catch (Exception e) {
            log.error("===>RateLimiterAspect#rateMode,e", e);
        }
        return true;
    }

    /**
     * 初始化完成获取验证码
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        if (applicationContext != null) {
            /*Cluster jimClient = applicationContext.getBean(Cluster.class);
            if (jimClient != null) {
                RateLimiterAspect.sha = jimClient.scriptLoad(luaScript);
                log.info("===>RateLimiterAspect#onApplicationEvent,初始化完成尝试往jimDB上传lua脚本，此时获取到的脚本验证码sha={}", RateLimiterAspect.sha);
            }*/
        }
    }

    /**
     * lua脚本保证原子性
     */
    private static String luaScript = "local key = KEYS[1]\n" +
            "local limit = tonumber(ARGV[1])\n" +
            "local current = tonumber(redis.call('get',key) or \"0\")\n" +
            "if current +1>limit then\n" +
            "    return 0\n" +
            "else\n" +
            "    redis.call(\"INCRBY\",key,\"1\")\n" +
            "    redis.call(\"expire\",key,\"2\")\n" +
            "    return 1\n" +
            "end";


}
