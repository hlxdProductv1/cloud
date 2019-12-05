package com.hlxd.microcloud.mapper;

import com.hlxd.microcloud.entity.Constant;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

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
