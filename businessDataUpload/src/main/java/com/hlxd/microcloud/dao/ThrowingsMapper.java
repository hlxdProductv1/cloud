package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.entity.Throwing;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 * 制丝
 * @Author taojun
 * @Date 2019/11/2116:34
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Mapper
public interface ThrowingsMapper {

    Throwing getThrowDetails(Map map);

    void batchInsert(List<Throwing> throwings);
}
