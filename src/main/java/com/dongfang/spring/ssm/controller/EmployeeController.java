package com.dongfang.spring.ssm.controller;

import com.dongfang.spring.ssm.bean.Employee;
import com.dongfang.spring.ssm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping(path = "/rest")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(path = "/employees", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @RequestMapping(path = "/employees", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void updateEmployees(@RequestBody List<Employee> employees) {
        employees.forEach(System.out::println);
        employeeService.updateEmployees(employees);
    }
}
