package com.dongfang.spring.mvc.bean;

public class Employee {
    private Integer empId;
    private String empName;
    private Double salary;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"empId\":")
                .append(empId);
        sb.append(",\"empName\":\"")
                .append(empName).append('\"');
        sb.append(",\"salary\":")
                .append(salary);
        sb.append('}');
        return sb.toString();
    }
}
