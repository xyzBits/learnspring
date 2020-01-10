package com.dongfang.spring.aop.transaction;

import com.dongfang.spring.aop.bean.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

//@Service
public class BookService {
    @Autowired
    private BookDao bookDao;

    /**
     * 结账：哪个用户买了哪本书
     */

    /**
     * Propagation propagation() default Propagation.REQUIRED;     事务的传播行为
     *      -- 传播行为（事务的传播 + 事务的行为）
     *              如果有多个事务嵌套运行，子事务是否要和大事务共用一个事务
     *                  tx_a() {
     *                      tx_b() {
     *                          tx_c() {
     *
     *                          }
     *                      }
     *                  }
     */

    // Propagation.REQUIRED 当前方法需要事务，如果调这个方法的地方有事务，用这个事务，如果没有，新建一个事务
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateBookPrice(String isbn, int price) {
        bookDao.updateBalance(isbn, price);
        int i = 10 / 0;

    }

    /**
     * 事务细节
     *     Propagation propagation() default Propagation.REQUIRED;     事务的传播行为
     *     Isolation isolation() default Isolation.DEFAULT;            事务的隔离级别
     *
     *     int timeout() default -1;                                   超时，事务超出指定时长后自动终止并回滚，单位秒
     *     boolean readOnly() default false;                           设置事务为只读事务
     *                                                                      可以进行事务优化，加快查询速度，不用管事务那一堆东西
     *                                                                      如果有增删改，设置为true会出异常
     *
     *     Class<? extends Throwable>[] rollbackFor() default {};      哪些异常事务需要回滚
     *     String[] rollbackForClassName() default {};
     *     Class<? extends Throwable>[] noRollbackFor() default {};    哪些异常事务可以不回滚
     *     String[] noRollbackForClassName() default {};
     *
     *          异常分类：
     *              运行时异常：可以不用处理，默认都回滚
     *              编译时异常：要么try-catch，要么在方法上声明throws
     *                        默认不回滚
     *          -- 事务的回滚：默认发生运行时异常都回滚，发生编译时异常不回滚
     *                  -- noRollbackFor 让原来默认回滚的运行时异常，不让它回滚--某此运行时异常不回滚
     *                  -- rollbackFor   让原本不回滚的编译时异常，现在让它回滚
     */
    @Transactional(timeout = 3, readOnly = false, noRollbackFor = {NullPointerException.class},
    rollbackFor = {FileNotFoundException.class}, propagation = Propagation.REQUIRES_NEW)
    public void checkout(String userName, String isbn) throws FileNotFoundException {
        // 1、减库存
        bookDao.updateStock(isbn);

        int price = bookDao.getPrice(isbn);

//        sleep(4);

        // 2、减余额
        bookDao.updateBalance(userName, price);

//        new FileInputStream("");
    }


    /**
     * 根据业务特性调整隔离级别
     *
     *     READ_UNCOMMITTED(1),      读未提交  读取到别的事务还未提交的数据，读到了还未提交的脏数据
     *     READ_COMMITTED(2),        读已提交  读取到其他事务已经提交的数据，未提交的读不到，这样避免，但是读取线程两次读取结果可能不一样，
     *                                        没有避免不可重复读，可重复读就是事务期间，每次读取的数据一样
     *     REPEATABLE_READ(4),       重复读    只要是一个事务期间，读多少次，数据都一样，外界删除了数据，都不影响，快照读
     *     SERIALIZABLE(8);
     *
     *
     *     两个事务并发修改同一个数据：
     *              一个开启事务，修改数据后，另一个修改就等待
     */
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public int getPrice(String isbn) {
        return bookDao.getPrice(isbn);
    }

    // 超过timeout，超时，事务进行回滚
    // org.springframework.transaction.TransactionTimedOutException
    private void sleep(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
