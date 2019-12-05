package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.entity.Constant;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/12/511:05
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Mapper
public interface ConstantMapper {

    List<Constant> getConstant();
}
