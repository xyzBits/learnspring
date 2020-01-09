package com.dongfang.spring.aop.bean;

import org.hamcrest.core.Is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 减去某个用户的余额
     *
     * org.springframework.dao.TransientDataAccessResourceException: PreparedStatementCallback;
     * SQL [update ## account set balance = balance - ? where username = ?];
     * Parameter index out of range (1 > number of parameters, which is 0).;
     * nested exception is java.sql.SQLException: Parameter index out of range (1 > number of parameters, which is 0).
     * String sql = "update ## account set balance = balance - ? where username = ?";
     * sql语句写错，这个方法执行出错，在它前面的方法执行成功了
     */
    public void updateBalance(String userName, int price) {
        String sql = "update account set balance = balance - ? where username = ?";
        jdbcTemplate.update(sql, price, userName);
    }

    /**
     * 获取某本图书的价格
     * @return
     */
    public int getPrice(String isbn) {
        String sql = "select price from book where isbn = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, isbn);
    }

    /**
     * 减去某本书的库存，每次减一本
     */
    public void updateStock(String isbn) {
        String sql = "update book_stock set stock = stock - 1 where isbn = ?";
        jdbcTemplate.update(sql, isbn);
    }

    /**
     * 修改图书价格
     * @param isbn
     * @param price
     */
    public void updateBookPrice(String isbn, int price) {
        String sql = "update book set price = ? where isbn = ?";
        jdbcTemplate.update(sql, price, isbn);
    }
}
