package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.entity.Reject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/12/1015:50
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Mapper
public interface RejectMapper {
    void batchInsert(List<Reject> rejectList);
}
