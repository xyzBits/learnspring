package com.dongfang.spring.aop.transaction;


import com.dongfang.spring.aop.transaction.dao.BookMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/aop/transaction/spring-mybatis-transaction.xml")
public class MyBatisSpringTransaction{

    @Autowired
    private MyBatisBookService myBatisBookService;

    @Test
    public void testSpringMybatisEnvironment() {
        System.out.println("myBatisBookService.getClass() = " + myBatisBookService.getClass());
    }

    @Test
    public void testMyBatisTransaction() {
        myBatisBookService.checkout("Tom", "ISBN-001");
    }
}
