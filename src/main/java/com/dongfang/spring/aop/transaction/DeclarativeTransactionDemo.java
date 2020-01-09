package com.dongfang.spring.aop.transaction;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileNotFoundException;


/**
 * 事务：
 *      -- 编程式事务；
 *                  >获取连接
 *                  >设置非自动提交
 *                  >sql执行
 *                  >手动提交
 *                  >>异常：回滚
 *                  >>关闭连接释放资源
 *
 *       -- 声明式事务
 *              以前通过复杂的编程来编写事务，替换为只需要告诉Spring哪个方法是事务方法即可，
 *              Spring自动进行事务控制，声明就是告诉Spring哪个方法是事务方法
 *
 *              -- 使用AOP的环绕通知去实现
 *                  // 获取连接
 *                  // 设置非自动提交
 *                  目标代码执行
 *                  // 正常提交
 *                  // 异常回滚
 *                  // 最终关闭
 *
 *
 *              --  @this is a transaction-method @Transactional
 *                  public void checkout(String userName, String isbn) {
 *                           }
 *              -- 事务管理代码的固定模式作为一种横切关注点，可能通过AOP方法模块化，进而借助Spring AOP框架实现声明式事务管理
 *                  事务管理器====事务切面
 *                  Spring已经提供了各种数据库框架的切面
 *                  这个事务管理器就可以在目标方法运行前后霆事务控制（事务切面）
 *
 *                  我们使用DataSourceTransactionManager即可
 *                      添加事务：
 *                          1、配置出事务管理器，让它工作
 *
 *
 *
 */

public class DeclarativeTransactionDemo {
    private ApplicationContext iocContainer;

    @Before
    public void prepare() {
        String resource = "spring/aop/transaction/spring-jdbc-template.xml";
        iocContainer = new ClassPathXmlApplicationContext(resource);
    }


    @Test
    public void testEnvironment() throws FileNotFoundException {
        BookService bookService = iocContainer.getBean(BookService.class);
        bookService.checkout("Tom", "ISBN-001");
    }

    @Test
    public void testTransactional() throws FileNotFoundException {
        BookService bookService = iocContainer.getBean(BookService.class);
        bookService.checkout("Tom", "ISBN-001");
    }

    /**
     * isolation = Isolation.READ_UNCOMMITTED
     * start transaction;
     * update book set price = 998 where isbn = 'ISBN-001';
     * price = 998
     * 隔离级别为读未提交，
     *
     * isolation = Isolation.READ_COMMITTED
     * 隔离级别改成读已提交，price = 100
     */
    @Test
    public void testIsolationReadUnCommit() {
        BookService bookService = iocContainer.getBean(BookService.class);
        int price = bookService.getPrice("ISBN-001");
        System.out.println("price = " + price);
        // cglib生成的代理对象执行真正的方法，有事务的业务逻辑，容器中保存的是代理对象
        // bookService.getClass() = class com.dongfang.spring.aop.transaction.BookService$$EnhancerBySpringCGLIB$$26cf3cb1
        System.out.println("bookService.getClass() = " + bookService.getClass());
    }

    @Test
    public void testPropagation() throws FileNotFoundException {
        MultiService multiService = iocContainer.getBean(MultiService.class);
        multiService.multiTransaction();

    }
}
