package com.dongfang.spring.mvc.controller;

import com.dongfang.spring.mvc.bean.Book;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 运行流程
 *      1、Dispatcher前端控制器收到所有请求
 *      2、查看请求和@RequestMapping标注的哪个url匹配
 *      3、找到目标方法后，执行
 *      4、拿到返回值，拼成页面地址，DispatcherServlet转发到页面
 */
/**
 * 为当前类的所有方法的请求地址指定一个基准路径
 * ANT风格的路径
 * ? 问题匹配一个字符，不能没有
 *      模糊和精确多个情况下，精确优先
 * * 匹配0-N个字符，任意多个字符，还可以匹配多层路径
 * ** 匹配一层路径
 *
 * slice/template/{id} 路径上写占位符，只能占一层路径
 *
 *       原始分格                REST风格
 *      /getBook?id=1          GET      /book/1
 *      /updateBook?id=1       PUT      /book/1
 *      /addBook?id=1          POST     /book/1
 *      /deleteBook?id=1       DELETE   /book/1
 *
 *      RequestParam
 *      可以直接在方法参数上写原生API，有HttpServlet HttpServletResponse HttpSession Principal Locale InputStream OutputStream Reader Writer
 */
@Controller
@RequestMapping(path = "/rest")
public class RestController {

    // world 404
    @RequestMapping(
            path = {"/hello?", "/world/a*/hi", "slice/mgr/**/hello/world", "slice/template/{id}/download"},
            method = {RequestMethod.GET, RequestMethod.POST},
            params = {"userName=dongfang", "password", "!age"},
            headers = {"x-auth-token=123456"}
/*            consumes = {""},
            produces = {""}
            指定Content-Type*/
    )
    public String hello(@PathVariable(value = "id", required = true) Integer id,
                        @RequestParam(value = "account", required = false, defaultValue = "xi fang shi bai") String account,
                        @RequestHeader(value = "User-Agent", required = true) String userAgent,
                        @CookieValue(value = "JESSIONID", required = true, defaultValue = "da sha bi") String jeSessionId) {
        System.out.println("访问成功");
        System.out.println("Template id is " + id);
        System.out.println("account is " + account);
        System.out.println("User-Agent is " + userAgent);
        System.out.println("JESSIONID is " + jeSessionId);
        return "success";
    }

    @RequestMapping(
            path = {"/book"},
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Book addBook(@RequestBody Book book,
                        HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("增加图书");
        System.out.println("图书信息是 " + book);
        System.out.println("request.getRequestURL() = " + request.getRequestURL());
        book.setBookName("东方");
        return book;
    }

    @RequestMapping(path = {"/book/{bookId}"}, method = RequestMethod.DELETE)
    public String deleteBookById(@PathVariable("bookId") Integer bookId) {
        System.out.println("删除 " + bookId + " 号图书");
        return "success";
    }

    @RequestMapping(path = {"/book/{bookId}"}, method = RequestMethod.PUT)
    public String updateBookById(@PathVariable("bookId") Integer bookId) {
        System.out.println("更新 " + bookId + " 号图书");
        return "success";
    }

    @RequestMapping(path = {"/book/{bookId}"}, method = RequestMethod.GET)
    public String getBookById(@PathVariable("bookId") Integer bookId) {
        System.out.println("查询 " + bookId + " 号图书");
        return "success";
    }


    /**
     * map.getClass() = class org.springframework.validation.support.BindingAwareModelMap
     * model.getClass() = class org.springframework.validation.support.BindingAwareModelMap
     * modelMap.getClass() = class org.springframework.validation.support.BindingAwareModelMap
     * */
    @RequestMapping(path = "/output")
    public String outputToPage(Map<String, Object> map,
                                            Model model,
                                            ModelMap modelMap) {
        map.put("name", "Dong fang");
        System.out.println("map.getClass() = " + map.getClass());
        System.out.println("model.getClass() = " + model.getClass());
        System.out.println("modelMap.getClass() = " + modelMap.getClass());
        return "success";
    }

}