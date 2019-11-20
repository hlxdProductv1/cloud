package com.hlxd.microcloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/***
 * -后台管理
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月18日
 */
@SpringBootApplication
//自动注入
@EnableAutoConfiguration
@MapperScan("com.hlxd.microcloud.mapper")
//事务管理
@EnableTransactionManagement
//线程池注解
@EnableAsync
public class BackstageServerApplication {
    public static void main(String[] args) throws Exception{
        SpringApplication.run(BackstageServerApplication.class, args);
    }

}
