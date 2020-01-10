package com.dongfang.spring.aop.transaction.dao;

import org.apache.ibatis.annotations.Param;

public interface BookMapper {
    void updateBalanceByUserNameAndBookPrice(@Param("userName") String userName, @Param("price") int price);

    int getBookPriceByIsbn(@Param("isbn") String isbn);

    void updateBookStockByIsbn(@Param("isbn") String isbn);

    void updateBookPriceByIsbn(@Param("isbn") String isbn, @Param("price") int price);
}
