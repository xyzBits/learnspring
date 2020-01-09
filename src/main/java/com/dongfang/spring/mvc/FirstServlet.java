package com.dongfang.spring.mvc;

import com.dongfang.spring.aop.bean.Employee;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FirstServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        WebApplicationContext iocContainer = ContextLoader.getCurrentWebApplicationContext();
        JdbcTemplate jdbcTemplate = iocContainer.getBean(JdbcTemplate.class);
        String sql = "select emp_id empId, emp_name empName, salary from employee where salary > ?";
        BeanPropertyRowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        List<Employee> employees = jdbcTemplate.query(sql, rowMapper, 4000.00);
        employees.forEach(System.out::println);
        resp.getWriter().write(employees.toString());
    }
}
