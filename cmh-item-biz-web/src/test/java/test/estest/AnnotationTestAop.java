package test.estest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author：
 * @data：
 * @description：
 */
@Aspect
@Component
public class AnnotationTestAop {

    @Around("@annotation(opLogCollect)")
    public Object opLogCollect(final ProceedingJoinPoint pjp, AnnotationTest opLogCollect) throws Throwable {
        System.out.println("Hello World");
        return null;
    }
}
