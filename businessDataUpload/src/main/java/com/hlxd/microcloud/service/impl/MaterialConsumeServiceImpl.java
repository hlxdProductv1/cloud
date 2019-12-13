package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.MaterialConsumeMapper;
import com.hlxd.microcloud.entity.MaterialConsume;
import com.hlxd.microcloud.entity.MaterialConsumeDetails;
import com.hlxd.microcloud.service.IMaterialConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/12/1016:20
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Service
public class MaterialConsumeServiceImpl implements IMaterialConsumeService {
    @Autowired
    private MaterialConsumeMapper materialConsumeMapper;

    @Override
    public void batchInsert(List<MaterialConsume> materialConsumes) {
        materialConsumeMapper.batchInsert(materialConsumes);
    }

    @Override
    public void batchInsertDetails(List<MaterialConsumeDetails> materialConsumeDetails) {
        materialConsumeMapper.batchInsertDetails(materialConsumeDetails);
    }
}
