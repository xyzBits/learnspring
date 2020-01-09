package com.dongfang.spring.aop.transaction;

import com.dongfang.spring.aop.bean.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.interfaces.PBEKey;

@Service
public class BookService {
    @Autowired
    private BookDao bookDao;

    /**
     * 结账：哪个用户买了哪本书
     */
    @Transactional
    public void checkout(String userName, String isbn) {
        // 1、减库存
        bookDao.updateStock(isbn);

        int price = bookDao.getPrice(isbn);

        // 2、减余额
        bookDao.updateBalance(userName, price);
    }
}
