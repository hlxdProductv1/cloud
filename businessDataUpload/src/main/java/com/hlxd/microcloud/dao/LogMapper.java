package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.entity.Log;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/12/1116:30
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Mapper
public interface LogMapper {

    List<Log> getLog(Map map);

    void insertLog(Log log);
}
