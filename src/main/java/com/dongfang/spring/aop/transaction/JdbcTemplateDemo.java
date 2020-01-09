package com.dongfang.spring.aop.transaction;

import com.dongfang.spring.aop.bean.Employee;
import com.dongfang.spring.aop.bean.EmployeeDao;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcTemplateDemo {
    private ApplicationContext iocContainer;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Before
    public void prepare() {
        String resource = "spring/aop/transaction/spring-jdbc-template.xml";
        iocContainer = new ClassPathXmlApplicationContext(resource);
        jdbcTemplate = iocContainer.getBean(JdbcTemplate.class);
        namedParameterJdbcTemplate = iocContainer.getBean(NamedParameterJdbcTemplate.class);
    }

    @Test
    public void testGetDataSource() throws SQLException {
        ComboPooledDataSource dataSource = iocContainer.getBean(ComboPooledDataSource.class);
        System.out.println("dataSource.getClass() = " + dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println("connection = " + connection);
    }

    @Test
    public void testGetJdbcTemplate() {
        System.out.println("jdbcTemplate = " + jdbcTemplate);
    }

    /**
     * 更新数据库中的一条记录
     */
    @Test
    public void testUpdateRecord() {
        String sql = "update employee set salary = ? where emp_id = ?";
        int affectedNum = jdbcTemplate.update(sql, 1300.00, 5);
        System.out.println("affectedNum = " + affectedNum);
    }

    /**
     * batchArgs 的长度就是sql要执行的次数
     * Object[] 每次执行sql的参数
     * */
    @Test
    public void testBatchInsert() {
        String sql = "insert into employee (emp_name, salary) values (?, ?)";
        List<Object[]> batchArgs = new ArrayList<>();
        batchArgs.add(new Object[]{"张三", 1998.98});
        batchArgs.add(new Object[]{"李四", 2998.98});
        batchArgs.add(new Object[]{"王五", 3998.98});
        batchArgs.add(new Object[]{"赵六", 4998.98});
        int[] affectedNum = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println("affectedNum = " + Arrays.toString(affectedNum));
    }

    /**
     * 查询一条数据，并封装成为一个java对象
     * java bean 的字段名必须和数据库中的字段名一致，如果不一致，要起别名，否则无法完成封装
     *
     * jdbcTemplate对查询在方法级别进行了区分
     *      查询集合         jdbcTemplate.query()
     *      查询单个对象      jdbcTemplate.queryForObject()
     *
     *      BeanPropertyRowMapper 定义数据库中的每一行记录和java bean的映射关系
     *          如果是JDK自带类型，则写type.class
     *      如果查询没结果：EmptyResultDataAccessException
     */
    @Test
    public void testSelectOneAndEncapsulation() {
        String sql = "select emp_id empId, emp_name empName, salary from employee where emp_id = ?";
        BeanPropertyRowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        Employee employee = jdbcTemplate.queryForObject(sql, rowMapper, 5);
        System.out.println("employee = " + employee);
    }

    /**
     * 批量查询时，rowMapper还是写集合中元素的类型
     * */
    @Test
    public void testBatchSelect() {
        String sql = "select emp_id empId, emp_name empName, salary from employee where salary > ?";
        BeanPropertyRowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        List<Employee> employees = jdbcTemplate.query(sql, rowMapper, 4000.00);
        employees.forEach(System.out::println);
    }

    /**
     * 查询无论是返回单个数据，或者单个对象，都使用queryForObject
     */
    @Test
    public void testSelectMaxSalary() {
        String sql = "select max(salary) from employee";
        Double maxSalary = jdbcTemplate.queryForObject(sql, Double.class);
        System.out.println("maxSalary = " + maxSalary);
    }

    /**
     * insert into employee (emp_name, salary) values (?, ?)
     *      ?是占位符参数，如果参数很多，容易出错，传参数的时候一定注意
     *
     *      具名参数：具有名字的参数，参数不是占位符了，而是一个变量名
     *              语法格式 :参数名  是map的key，参数值是value
     *
     */
    @Test
    public void testNamedParameter() {
        String sql = "insert into employee (emp_name, salary) values (:empName, :salary)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("empName", "东方");
        paramMap.put("salary", 29888.8);
        int affectedNum = namedParameterJdbcTemplate.update(sql, paramMap);
        System.out.println("affectedNum = " + affectedNum);
    }


    /**
     * 以sql parameter source的形式插入数据
     *      BeanPropertySqlParameterSource 不用往map中装参数
     */
    @Test
    public void testSqlParameterSource() {
        String sql = "insert into employee (emp_name, salary) values (:empName, :salary)";
        Employee employee = new Employee();
        employee.setEmpName("田七");
        employee.setSalary(3243.34);

        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(employee);
        int affectedNum = namedParameterJdbcTemplate.update(sql, parameterSource);
        System.out.println("affectedNum = " + affectedNum);
    }

    @Test
    public void testUseDao() {
        EmployeeDao employeeDao = iocContainer.getBean(EmployeeDao.class);
        Employee employee = new Employee();
        employee.setEmpName("田九");
        employee.setSalary(3243.34);
        employeeDao.saveEmployee(employee);
    }
}
