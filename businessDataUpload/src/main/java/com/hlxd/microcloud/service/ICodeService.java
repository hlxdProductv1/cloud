package com.hlxd.microcloud.service;

import com.hlxd.microcloud.entity.ProCode;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2115:56
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
public interface ICodeService {

    ProCode getCodeDetails(Map map);

    void batchInsert(List<ProCode> proCodes);

}
