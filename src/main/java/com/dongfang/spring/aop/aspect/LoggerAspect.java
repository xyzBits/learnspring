package com.dongfang.spring.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
/**
 *
 * 1	AOP概述
 * ●AOP(Aspect-Oriented Programming，面向切面编程)：是一种新的方法论，是对传统 OOP(Object-Oriented Programming，面向对象编程)的补充。
 * ●AOP编程操作的主要对象是切面(aspect)，而切面模块化横切关注点。
 * ●在应用AOP编程时，仍然需要定义公共功能，但可以明确的定义这个功能应用在哪里，以什么方式应用，并且不必修改受影响的类。这样一来横切关注点就被模块化到特殊的类里——这样的类我们通常称之为“切面”。
 * ●AOP的好处：
 * ○每个事物逻辑位于一个位置，代码不分散，便于维护和升级
 * ○业务模块更简洁，只包含核心业务代码
 *
 * 2	AOP术语
 * 2.1	横切关注点
 * 从每个方法中抽取出来的同一类非核心业务。
 * 2.2	切面(Aspect)
 * 封装横切关注点信息的类，每个关注点体现为一个通知方法。
 * 2.3	通知(Advice)
 * 切面必须要完成的各个具体工作
 * 2.4	目标(Target)
 * 被通知的对象
 * 2.5	代理(Proxy)
 * 向目标对象应用通知之后创建的代理对象
 * 2.6	连接点(Joinpoint)
 * 横切关注点在程序代码中的具体体现，对应程序执行的某个特定位置。例如：类某个方法调用前、调用后、方法捕获到异常后等。
 * 在应用程序中可以使用横纵两个坐标来定位一个具体的连接点：
 *
 * 3.3	用AspectJ注解声明切面
 * ①要在Spring中声明AspectJ切面，只需要在IOC容器中将切面声明为bean实例。②当在Spring IOC容器中初始化AspectJ切面之后，Spring IOC容器就会为那些与 AspectJ切面相匹配的bean创建代理。
 * ③在AspectJ注解中，切面只是一个带有@Aspect注解的Java类，它往往要包含很多通知。
 * ④通知是标注有某种注解的简单的Java方法。
 * ⑤AspectJ支持5种类型的通知注解：
 * [1]@Before：前置通知，在方法执行之前执行
 * [2]@After：后置通知，在方法执行之后执行
 * [3]@AfterRunning：返回通知，在方法返回结果之后执行
 * [4]@AfterThrowing：异常通知，在方法抛出异常之后执行
 * [5]@Around：环绕通知，围绕着方法执行
 */

/**
 * 3	通知
 * 3.1	概述
 * 	在具体的连接点上要执行的操作。
 * 	一个切面可以包括一个或者多个通知。
 * 	通知所使用的注解的值往往是切入点表达式。
 * 3.2	前置通知
 * 	前置通知：在方法执行之前执行的通知
 * 	使用@Before注解
 * 3.3	后置通知
 * 	后置通知：后置通知是在连接点完成之后执行的，即连接点返回结果或者抛出异常的时候
 * 	使用@After注解
 * 3.4	返回通知
 * 	返回通知：无论连接点是正常返回还是抛出异常，后置通知都会执行。如果只想在连接点返回的时候记录日志，应使用返回通知代替后置通知。
 * 	使用@AfterReturning注解
 * 	在返回通知中访问连接点的返回值
 * 	在返回通知中，只要将returning属性添加到@AfterReturning注解中，就可以访问连接点的返回值。该属性的值即为用来传入返回值的参数名称
 * 	必须在通知方法的签名中添加一个同名参数。在运行时Spring AOP会通过这个参数传递返回值
 * 	原始的切点表达式需要出现在pointcut属性中
 *
 * 3.5	异常通知
 * 	异常通知：只在连接点抛出异常时才执行异常通知
 * 	将throwing属性添加到@AfterThrowing注解中，也可以访问连接点抛出的异常。Throwable是所有错误和异常类的顶级父类，所以在异常通知方法可以捕获到任何错误和异常。
 * 	如果只对某种特殊的异常类型感兴趣，可以将参数声明为其他异常的参数类型。然后通知就只在抛出这个类型及其子类的异常时才被执行
 *
 * 3.6	环绕通知
 * 	环绕通知是所有通知类型中功能最为强大的，能够全面地控制连接点，甚至可以控制是否执行连接点。
 * 	对于环绕通知来说，连接点的参数类型必须是ProceedingJoinPoint。它是 JoinPoint的子接口，允许控制何时执行，是否执行连接点。
 * 	在环绕通知中需要明确调用ProceedingJoinPoint的proceed()方法来执行被代理的方法。如果忘记这样做就会导致通知被执行了，但目标方法没有被执行。
 * 	注意：环绕通知的方法需要返回目标方法执行之后的结果，即调用 joinPoint.proceed();的返回值，否则会出现空指针异常。
 */
@Order(value = 1)
@Aspect
@Component
public class LoggerAspect {
    /**
     * 通过表达式的方式定位一个或多个具体的连接点
     *      execution([权限修饰符] [返回值类型] [简单类名/全类名] [方法名]([参数列表]))
     *      第一个“*”代表任意修饰符及任意返回值。
     *      第二个“*”代表任意方法。
     *      “..”匹配任意数量、任意类型的参数。
     *      权限修饰符不能省略
     * 重用切入点表达式，写成public其他类也可以用
     * 切入点表达式还可以用 &&  都成立才切入
     *                  ||  任意一个条件满足就切片
     *                  ！   满足这个条件的不切入
     *                  切入点表达式可以通过 “&&”、“||”、“!”等操作符结合起来。
     *
     */
    @Pointcut(value = "execution(public * com.dongfang.spring.aop.bean.*.*(..))")
    public void loggerPointcut() {
    }

    /**
     *
     * @param joinPoint 当前连接点所在方法的方法名、当前传入的参数值等等。这些信息都封装在JoinPoint接口的实例对象中
     */
    @Before(value = "loggerPointcut()")
    public void before(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        System.out.println("LoggerAspect 前置通知， 方法：" + signature + " 开始执行参数为：" + Arrays.toString(args));
    }

    @After(value = "loggerPointcut()")
    public void after(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        System.out.println("LoggerAspect 后置通知， 方法：" + signature + " 执行完了。");
    }

    @AfterReturning(value = "loggerPointcut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        Signature signature = joinPoint.getSignature();
        System.out.println("LoggerAspect 返回通知， 方法：" + signature + " 返回了，结果为：" + result);
    }

    @AfterThrowing(value = "loggerPointcut()", throwing = "ex")
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
