package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.LogMapper;
import com.hlxd.microcloud.entity.Log;
import com.hlxd.microcloud.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class LogServiceImpl implements ILogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public List<Log> getLog(Map map) {
        return logMapper.getLog(map);
    }

    @Override
    public void insertLog(Log log) {
        logMapper.insertLog(log);
    }
}
