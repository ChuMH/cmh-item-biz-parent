package com.cmh.item.biz.msg;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
@Documented
public @interface CacheKey {
}