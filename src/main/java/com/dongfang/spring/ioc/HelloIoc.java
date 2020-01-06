package com.dongfang.spring.ioc;

import com.dongfang.spring.ioc.bean.Person;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class HelloIoc {
    private static final String IOC_1_PATH = "D:\\ubuntu\\learn\\JavaWeb\\MavenProject\\maven03\\learnspring\\src\\main\\resources\\spring\\ioc\\ioc-1.xml";

    @Test
    public void testIoc001() {
        // ApplicationContext代表ioc容器
        ApplicationContext context = new FileSystemXmlApplicationContext(IOC_1_PATH);
        Person bean = context.getBean(Person.class);
        System.out.println("bean = " + bean);
    }
}
