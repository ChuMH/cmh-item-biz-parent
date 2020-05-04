package com.cmh.item.biz.msg.aop;

import com.cmh.item.biz.msg.Cacheable;
import com.cmh.item.biz.msg.json.SerializeUtil;
import com.cmh.item.biz.msg.utils.MD5Util;
import com.cmh.item.biz.msg.CacheKey;
import com.cmh.item.biz.msg.Cacheable.KeyMode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 缓存拦截器
 * @author 初明昊
 * @time 2019/12/19
 * @description 缓存注解拦截实现
 */
@Slf4j
@Aspect
@Component
public class CacheableAop {

    @Around("@annotation(cacheable)")
    public Object cached(final ProceedingJoinPoint joinPoint, Cacheable cacheable) throws Throwable {
        //生成和加密key
        String key = getCacheKey(joinPoint,cacheable);
        Class<?> cls = getReturnType(joinPoint);
        byte[] bytes;
        Object value;
        if(cls != null){
            //redis查询缓存
            //bytes = jedis.get(key.getBytes());
            bytes = null;
            if(bytes != null){
                //转换。byte[]->方法返回实际类型 cls 反序列化
                value = SerializeUtil.deserialize(bytes,cls);
                if(value != null){
                    log.info("===>Get value from redis cache, key = "+key);
                    //如果有数据,则直接返回
                    return value;
                }
            }
        }
        //跳过缓存，到后端查询数据。跳出，去执行目标方法
        value = joinPoint.proceed();
        //如果返回结果为null，直接返回结果
        if(value == null){
            return null;
        }
        bytes = SerializeUtil.serialize(value);
        //如果没有设置过期时间，无限期缓存
        if(cacheable.expire() <= 0){
            //redis set
            //jedis.set(key,value);
        }else{
            //否则设置缓存时间,单位(秒)
            //jedis.setEx(key.getBytes(),bytes,cache,expire(),TimeUnit.SECONDS);
        }
        return value;
    }
    /**
     *
     * @Description: 通过反射机制获取当前方法的返回值
     * @param pjp
     * @return
     */
    private Class<?> getReturnType(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Class<?> cl = method.getReturnType();
        if (Void.TYPE == cl) {
            return null;
        }
        return cl;
    }
    /**
     * 获取生成缓存的key值
     * @param joinPoint
     * @param cache
     * @return
     */
    private String getCacheKey(ProceedingJoinPoint joinPoint, Cacheable cache) {
        StringBuilder key = new StringBuilder();
        StringBuilder buf = new StringBuilder();
        //获取全路径名
        String className = joinPoint.getSignature().getDeclaringTypeName(); //com.test.controller.TestController
        //方法名称
        String methodName = joinPoint.getSignature().getName();
        buf.append(className).append(".").append(methodName);
        //如果入参key不为空，则添加
        if(cache.key().length() > 0){
            buf.append(".").append(cache.key());
        }
        //获取所有形参
        Object[] args = joinPoint.getArgs();
        if(cache.keyMode() == KeyMode.CACHEKEY){
            //反射中，获取方法参数上的注解
            Annotation[][] pas = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterAnnotations();
            for(int i=0;i < pas.length;i++){
                for(Annotation an : pas[i]){
                    if(an instanceof CacheKey){
                        if(args[i] != null){
                            buf.append(".").append(args[i].toString());
                            break;
                        }
                    }
                }
            }
        }else if(cache.keyMode() == KeyMode.BASIC){
            for(Object arg : args){
                if(arg != null){
                    if(arg instanceof String){
                        buf.append(".").append(arg);
                    }else if(arg instanceof Integer || arg instanceof Long || arg instanceof Short){
                        buf.append(".").append(arg.toString());
                    }else if(arg instanceof Boolean){
                        buf.append(".").append(arg.toString());
                    }
                }
            }
        }else if(cache.keyMode() == KeyMode.ALL){
            for(Object arg : args){
                if(arg != null){
                    buf.append(".").append(arg.toString());
                }
            }
        }
        //为key加前缀，有前缀添加前缀，无前缀用类名
        if(StringUtils.isNotBlank(cache.prefix())){
            key.append(cache.prefix());
        }
        key.append(className.substring(className.lastIndexOf(".")+1).trim());
        key.append(buf);
        if(StringUtils.isNotBlank(buf)){
            key.append(MD5Util.MD5(buf.toString()));
        }
        return key.toString();
    }
}
