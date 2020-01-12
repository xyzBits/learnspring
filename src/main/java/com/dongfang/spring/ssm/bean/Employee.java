package com.dongfang.spring.ssm.bean;

import java.util.Date;

public class Employee {
    private Integer empId;
    private String empName;
    private Double salary;
    private Date birth;
    private String email;
    private Integer gender;

    public Employee() {
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"empId\":")
                .append(empId);
        sb.append(",\"empName\":\"")
                .append(empName).append('\"');
        sb.append(",\"salary\":")
                .append(salary);
        sb.append(",\"birth\":\"")
                .append(birth).append('\"');
        sb.append(",\"email\":\"")
                .append(email).append('\"');
        sb.append(",\"gender\":")
                .append(gender);
        sb.append('}');
        return sb.toString();
    }
}
