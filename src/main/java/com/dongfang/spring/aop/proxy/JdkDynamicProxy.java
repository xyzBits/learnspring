package com.dongfang.spring.aop.proxy;

import com.dongfang.spring.aop.CalculatorImpl;
import com.dongfang.spring.aop.ICalculator;
import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.Arrays;

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
