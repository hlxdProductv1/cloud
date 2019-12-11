package com.hlxd.microcloud.service;

import com.hlxd.microcloud.dao.MaterialConsumeMapper;
import com.hlxd.microcloud.entity.MaterialConsume;
import com.hlxd.microcloud.entity.MaterialConsumeDetails;

import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/12/1014:59
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
public interface IMaterialConsumeService {

    void batchInsert(List<MaterialConsume> materialConsumes);


    void batchInsertDetails(List<MaterialConsumeDetails> materialConsumeDetails);
}
