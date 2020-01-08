package com.dongfang.spring.aop.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Order(value = 2)
@Aspect
@Component
public class ValidationAspect {

    // 使用别的类中定义好的切入点表达式
    @Before(value = "com.dongfang.spring.aop.aspect.LoggerAspect.loggerPointcut()")
    public void before(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        System.out.println("ValidationAspect 前置通知， 方法：" + signature + " 开始执行参数为：" + Arrays.toString(args));
    }
}
