package com.dongfang.spring.aop.transaction;

import com.sun.source.tree.LambdaExpressionTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.function.BinaryOperator;

//@Service
public class MultiService {
    @Autowired
    private BookService bookService;

    /**
     * REQUIRED 和大事务共用同一个事务
     * @throws FileNotFoundException
     */
    @Transactional
    public void multiTransaction() throws FileNotFoundException {
        // REQUIRED
        bookService.checkout("Tom", "ISBN-001");

        // REQUIRED
        bookService.updateBookPrice("ISBN-002", 998);
    }
}
