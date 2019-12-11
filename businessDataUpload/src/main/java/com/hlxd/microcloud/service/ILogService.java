package com.hlxd.microcloud.service;

import com.hlxd.microcloud.entity.Log;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/12/1116:31
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
public interface ILogService {

    List<Log> getLog(Map map);

    void insertLog(Log log);
}
