package com.cmh.item.biz.msg.aop;

import com.cmh.item.biz.msg.Sout;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SoutAop {

    /*@Pointcut("execution(public * com.test.controller.*.*(..))")
    public void pointCut(){}*/

    @Before("@annotation(sout)")
    public void sout(Sout sout){
        System.out.println("前置通知.........");
    }
}
