package com.hlxd.microcloud.util;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/10/16 14:34
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT uploadStatistic
 */
@Component
@Slf4j
public class DruidUtils {
    private static DruidDataSource dataSource = new DruidDataSource();
    // 声明线程共享变量
    public static ThreadLocal<Connection> container = new ThreadLocal<Connection>();



    @Value("${spring.datasource.username1}")
    private String user;

    @Value("${spring.datasource.password1}")
    private String pwd;

    @Value("${spring.datasource.url1}")
    private String url;

    private static final String dbName = "HHMESDB";

    // 配置说明，参考官方网址
    @PostConstruct
    public void init() {
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://134.175.90.151:3306/product_v2?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false");
        dataSource.setUsername("root");// 用户名
        dataSource.setPassword("Hlxd@123456");// 密码
        dataSource.setInitialSize(10);
        dataSource.setMaxActive(15);
        dataSource.setMinIdle(5);
        dataSource.setMaxWait(10);
    }

    /**
     * 获取数据连接
     *
     * @return
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            log.info(Thread.currentThread().getName() + "连接已经开启......");
            container.set(conn);
        } catch (Exception e) {
            log.error("连接获取失败");
            e.printStackTrace();
        }
        return conn;
    }

    /*** 获取当前线程上的连接开启事务 */
    public static void startTransaction() {
        Connection conn = container.get();// 首先获取当前线程的连接
        if (conn == null) {// 如果连接为空
            conn = getConnection();// 从连接池中获取连接
            container.set(conn);// 将此连接放在当前线程上
            log.info(Thread.currentThread().getName() + "空连接从dataSource获取连接");
        } else {
            log.info(Thread.currentThread().getName() + "从缓存中获取连接");
        }
        try {
            conn.setAutoCommit(false);// 开启事务
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 提交事务
    public static void commit() {
        try {
            Connection conn = container.get();// 从当前线程上获取连接if(conn!=null){//如果连接为空，则不做处理
            if (null != conn) {
                conn.commit();// 提交事务
                log.info(Thread.currentThread().getName() + "事务已经提交......");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*** 回滚事务 */
    public static void rollback() {
        try {
            Connection conn = container.get();// 检查当前线程是否存在连接
            if (conn != null) {
                conn.rollback();// 回滚事务
                container.remove();// 如果回滚了，就移除这个连接
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*** 关闭连接 */
    public static void close() {
        try {
            Connection conn = container.get();
            if (conn != null) {
                conn.close();
                log.info(Thread.currentThread().getName() + "连接关闭");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                container.remove();// 从当前线程移除连接切记
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


}
