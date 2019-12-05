package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.entity.Constant;
import com.hlxd.microcloud.mapper.ConstantMapper;
import com.hlxd.microcloud.service.IConstantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/12/511:04
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Service
public class ConstantServiceImpl implements IConstantService {
    @Autowired
    private ConstantMapper constantMapper;

    @Override
    public List<Constant> getConstant() {
        return constantMapper.getConstant();
    }
}
