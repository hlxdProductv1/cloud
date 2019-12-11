package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.entity.MaterialConsume;
import com.hlxd.microcloud.entity.MaterialConsumeDetails;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/12/1014:57
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Mapper
public interface MaterialConsumeMapper {

    void batchInsert(List<MaterialConsume> materialConsumes);


    void batchInsertDetails(List<MaterialConsumeDetails> materialConsumeDetails);
}
