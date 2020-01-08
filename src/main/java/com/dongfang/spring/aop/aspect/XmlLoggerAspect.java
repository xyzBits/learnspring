package com.dongfang.spring.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class XmlLoggerAspect {

    public void before(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        System.out.println("LoggerAspect 前置通知， 方法：" + signature + " 开始执行参数为：" + Arrays.toString(args));
    }

    public void after(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        System.out.println("LoggerAspect 后置通知， 方法：" + signature + " 执行完了。");
    }


    public void afterReturning(JoinPoint joinPoint, Object result) {
        Signature signature = joinPoint.getSignature();
        System.out.println("LoggerAspect 返回通知， 方法：" + signature + " 返回了，结果为：" + result);
    }


    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        Signature signature = joinPoint.getSignature();
        System.out.println("LoggerAspect 异常通知， 方法：" + signature + " 发生异常，异常为：" + ex);
    }

//    @Around(value = "loggerPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        try {
            System.out.println("LoggerAspect 环绕 前置通知， 方法：" + signature + " 开始执行参数为：" + Arrays.toString(args));
            Object result = joinPoint.proceed(args);
            System.out.println("LoggerAspect 环绕 返回通知， 方法：" + signature + " 返回了，结果为：" + result);
            return result;
        } catch (Throwable ex) {
            System.out.println("LoggerAspect 环绕 异常通知， 方法：" + signature + " 发生异常，异常为：" + ex);
            throw new RuntimeException(ex);
        } finally {
            System.out.println("LoggerAspect 环绕 后置通知， 方法：" + signature + " 执行完了。");
        }
    }
}
