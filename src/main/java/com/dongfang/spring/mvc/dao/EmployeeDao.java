package com.dongfang.spring.mvc.dao;

import com.dongfang.spring.mvc.bean.Employee;

import java.util.List;

public interface EmployeeDao {
    List<Employee> getAllEmployees();
}
