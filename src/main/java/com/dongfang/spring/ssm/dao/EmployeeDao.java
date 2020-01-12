package com.dongfang.spring.ssm.dao;

import com.dongfang.spring.ssm.bean.Employee;

import java.util.List;

public interface EmployeeDao {
    List<Employee> getAllEmployees();
}
