package com.dongfang.spring.ssm.dao;

import com.dongfang.spring.ssm.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeDao {
    List<Employee> getAllEmployees();

    void updateEmployees(@Param("employees") List<Employee> employees);
}
