package com.hlxd.microcloud.config;

import com.hlxd.microcloud.filter.AuthorizedRequestFilter;
import org.springframework.context.annotation.Bean;


//@Configuration
public class ZuulConfig {
    @Bean
    public AuthorizedRequestFilter getAuthorizedRequestFilter(){
        return new AuthorizedRequestFilter();
    }
}
