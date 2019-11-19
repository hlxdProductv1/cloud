package com.hlxd.microcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;

@Configuration
public class PageConfig {
	
   /***
    * mybatis-plus分页插件
    * @return
    */
   @Bean
   public PaginationInterceptor paginationInterceptor() {
       PaginationInterceptor page = new PaginationInterceptor();
       page.setDialectType("mysql");
       return page;
   }

}
