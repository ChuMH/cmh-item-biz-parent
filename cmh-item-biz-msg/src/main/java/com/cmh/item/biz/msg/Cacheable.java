package com.cmh.item.biz.msg;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Documented
public @interface Cacheable {
    public enum KeyMode {
        // 只有加了@CacheKey的参数,才加入key后缀中
        CACHEKEY,
        // 只有基本类型参数,才加入key后缀中,如:String,Integer,Long,Short,Boolean
        BASIC,
        // 所有参数都加入key后缀
        ALL;
    }
    //key前缀
    public String prefix() default "";
    // 缓存key
    public String key() default "";
    // key的后缀模式
    public KeyMode keyMode() default KeyMode.BASIC;
    // 缓存多少秒,默认10分钟
    public long expire() default 600L;
}
