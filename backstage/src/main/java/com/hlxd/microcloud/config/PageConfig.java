package com.hlxd.microcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;

/***
 * -mybatis-plus 分页配置
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
@Configuration
public class PageConfig {
	
   @Bean
   public PaginationInterceptor paginationInterceptor() {
       PaginationInterceptor page = new PaginationInterceptor();
       page.setDialectType("mysql");
       return page;
   }

}
