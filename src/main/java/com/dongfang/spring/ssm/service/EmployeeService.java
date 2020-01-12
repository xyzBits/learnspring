package com.dongfang.spring.ssm.service;

import com.dongfang.spring.ssm.bean.Employee;
import com.dongfang.spring.ssm.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;

    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateEmployees(List<Employee> employees) {
        employeeDao.updateEmployees(employees);
    }

}
