package com.hlxd.microcloud.config;

import com.hlxd.microcloud.entity.Constant;
import com.hlxd.microcloud.service.IConstantService;
import com.hlxd.microcloud.util.EHCacheUtil;
import com.hlxd.microcloud.util.JedisPoolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/12/511:10
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Component
public class ScheduledConfig {
    @Autowired
    private IConstantService iConstantService;


    /**
     *
     * 初始化配置项缓存
     *
     */
    @PostConstruct
    public void initCache(){
        List<Constant> constants = iConstantService.getConstant();
        JedisPoolUtils.delAllValue();
        for(Constant constant:constants){
            JedisPoolUtils.setPool(constant.getName(),constant.getValue());
        }
    }





}
