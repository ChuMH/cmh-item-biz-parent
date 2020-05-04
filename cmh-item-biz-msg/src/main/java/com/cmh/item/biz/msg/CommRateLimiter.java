package com.cmh.item.biz.msg;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CommRateLimiter {

    RateLimiterStrategy[] strategies() default {};

}
