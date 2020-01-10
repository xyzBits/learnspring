package com.dongfang.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 运行流程
 *      1、Dispatcher前端控制器收到所有请求
 *      2、查看请求和@RequestMapping标注的哪个url匹配
 *      3、找到目标方法后，执行
 *      4、拿到返回值，拼成页面地址，DispatcherServlet转发到页面
 */
@Controller
public class FirstController {

    @RequestMapping("/hello")
    public String firstRequest() {
        System.out.println("正在处理请求");
        // 视图解析器自动拼串
        return "success";
    }
}
