package com.hlxd.microcloud.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2214:53
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Data
public class FeedingRecord implements Serializable {
    /**
     * 主键
     *
     * */
    private String id;

    /**
     * 制丝批次
     * */
    private String throwingNumber;

    /**
     * 开始喂丝时间
     * */
    private String startFeedingDate;

    /**
     * 喂丝结束时间
     * */
    private String endFeedingDate;

    /**
     * 设备代码
     * */
    private String machineCode;

    /**
     * 设备名称
     * */
    private String machineName;

    /**
     * 喂丝设备编码
     * */
    private String feedingMachineCode;

    /**
     * 喂丝设备名称
     * */
    private String feedingMachineName;

    /**
     * 备注
     * */
    private String remark;

    /**
     * 工厂代码
     * */
    private String organizeCode;

    /**
     * 工厂名称
     * */
    private String organizeName;

}
