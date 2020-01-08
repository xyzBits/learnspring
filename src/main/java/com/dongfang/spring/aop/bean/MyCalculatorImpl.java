package com.dongfang.spring.aop.bean;

import org.springframework.stereotype.Component;

@Component
public class MyCalculatorImpl {
    public int add(int i, int j) {
        int result = i + j;
        System.out.println("result = " + result);
        return result;
    }


    public int sub(int i, int j) {
        int result = i - j;
        System.out.println("result = " + result);
        return result;
    }


    public int div(int i, int j) {
        int result = i / j;
        System.out.println("result = " + result);
        return result;
    }


    public int mul(int i, int j) {
        int result = i * j;
        System.out.println("result = " + result);
        return result;
    }
}
