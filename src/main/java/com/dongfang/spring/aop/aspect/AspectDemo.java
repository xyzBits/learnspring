package com.dongfang.spring.aop.aspect;

import com.dongfang.spring.aop.bean.CalculatorImpl;
import com.dongfang.spring.aop.bean.ICalculator;
import com.dongfang.spring.aop.bean.MyCalculatorImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AspectDemo {
    ApplicationContext container;

    @Before
    public void prepareIocContainer() {
        String resource = "spring/aop/spring-xml-aop.xml";
        container = new ClassPathXmlApplicationContext(resource);
    }

    @Test
    public void testAspect() {
        ICalculator calculator = container.getBean(ICalculator.class);
        int result = calculator.add(1, 2);
        // 使用JDK动态代理创建的对象
        // calculator.getClass() = class com.sun.proxy.$Proxy26
        System.out.println("calculator.getClass() = " + calculator.getClass());
    }

    @Test
    public void testClassProxy() {
        CalculatorImpl calculator = container.getBean(CalculatorImpl.class);
        System.out.println("calculator.getClass() = " + calculator.getClass());
        // No qualifying bean of type 'com.dongfang.spring.aop.bean.CalculatorImpl' available
        // 实现了接口的，无法用类.class获取到
    }

    @Test
    public void testGetClassProxy() {
        MyCalculatorImpl myCalculator = container.getBean(MyCalculatorImpl.class);
        myCalculator.add(1, 2);
        System.out.println("myCalculator.getClass() = " + myCalculator.getClass());
        // myCalculator.getClass() = class com.dongfang.spring.aop.bean.MyCalculatorImpl$$EnhancerBySpringCGLIB$$1ccee6d0
        // MyCalculatorImpl没有实现任何接口，通过aop也生成了代理对象，是由CGLIB实现的
    }

    @Test
    public void configAspectByXml() {
        MyCalculatorImpl calculator = container.getBean(MyCalculatorImpl.class);
        calculator.add(1, 2);
        System.out.println("calculator.getClass() = " + calculator.getClass());
    }
}
