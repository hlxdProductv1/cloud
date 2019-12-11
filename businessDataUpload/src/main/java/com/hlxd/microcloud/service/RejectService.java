package com.hlxd.microcloud.service;

import com.hlxd.microcloud.entity.Reject;

import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/12/1015:51
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
public interface RejectService {
    void batchInsert(List<Reject> rejectList);

}
