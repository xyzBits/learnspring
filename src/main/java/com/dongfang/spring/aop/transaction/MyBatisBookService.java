package com.dongfang.spring.aop.transaction;

import com.dongfang.spring.aop.transaction.dao.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;

@Service
public class MyBatisBookService {
    @Autowired
    private BookMapper bookMapper;

    @Transactional(timeout = 3, noRollbackFor = {NullPointerException.class},
            rollbackFor = {FileNotFoundException.class}, propagation = Propagation.REQUIRES_NEW)
    public void checkout(String userName, String isbn) {
        bookMapper.updateBookStockByIsbn(isbn);

        int price = bookMapper.getBookPriceByIsbn(isbn);

        bookMapper.updateBalanceByUserNameAndBookPrice(userName, price);

    }
}
