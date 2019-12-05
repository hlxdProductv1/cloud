package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.WrapMapper;
import com.hlxd.microcloud.entity.WrapOrder;
import com.hlxd.microcloud.service.WrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2914:02
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Service
public class WrapServiceImpl implements WrapService {
    @Autowired
    private WrapMapper wrapMapper;


    @Override
    public WrapOrder getWrapOrder(Map map) {
        return wrapMapper.getWrapOrder(map);

    }
}
