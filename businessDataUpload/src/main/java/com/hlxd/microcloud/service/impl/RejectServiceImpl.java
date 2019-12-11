package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.RejectMapper;
import com.hlxd.microcloud.entity.Reject;
import com.hlxd.microcloud.service.RejectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/12/1015:52
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Service
public class RejectServiceImpl implements RejectService {
    @Autowired
    private RejectMapper rejectMapper;

    @Override
    public void batchInsert(List<Reject> rejectList) {
        rejectMapper.batchInsert(rejectList);
    }
}
