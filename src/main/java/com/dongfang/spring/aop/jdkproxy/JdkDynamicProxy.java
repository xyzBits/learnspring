package com.dongfang.spring.aop.jdkproxy;

import com.dongfang.spring.aop.bean.CalculatorImpl;
import com.dongfang.spring.aop.bean.ICalculator;
import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * 2	动态代理
 * 代理设计模式的原理：使用一个代理将对象包装起来，然后用该代理对象取代原始对象。
 * 任何对原始对象的调用都要通过代理。代理对象决定是否以及何时将方法调用转到原始对象上。
 */
@SuppressWarnings("all")
public class JdkDynamicProxy {
    public static <T> T getProxyObject(T target) {
        Class<T> clazz = (Class<T>) target.getClass();
        T proxyInstance = (T) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), (proxy, method, args) -> {
            T result = null;
            try {
                System.out.println("The method " + method.getName() + "() begin with " + Arrays.toString(args));
                result = (T) method.invoke(target, args);
                System.out.println("The method " + method.getName() + "() end with " + result);
            } catch (Exception e) {
                System.out.println("The method " + method.getName() + "() execute with error, " + " error is " + e);
            } finally {
                System.out.println("The method " + method.getName() + "() execute finish");
            }
            return result;
        });
        return proxyInstance;
    }

    @Test
    public void testJdkDyanmicProxy() {
        ICalculator calculator = new CalculatorImpl();
        ICalculator proxy = getProxyObject(calculator);
        System.out.println("proxy.getClass() = " + proxy.getClass());
        // proxy.getClass() = class com.sun.proxy.$Proxy7
        proxy.add(1, 2);
    }
}
