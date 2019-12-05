package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.entity.FeedingRecord;
import com.hlxd.microcloud.entity.ProCode;
import com.hlxd.microcloud.entity.Throwing;
import com.hlxd.microcloud.entity.WrapOrder;
import com.hlxd.microcloud.service.ICodeService;
import com.hlxd.microcloud.service.IFeedingService;
import com.hlxd.microcloud.service.IThrowingService;
import com.hlxd.microcloud.service.WrapService;
import com.hlxd.microcloud.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2914:10
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@RestController
@RequestMapping("/pro")
public class ProCodeController {


    @Value("${timeBreak}")
    private String timeBreak;

    @Autowired
    private WrapService wrapService;

    @Autowired
    private ICodeService iCodeService;

    @Autowired
    private IThrowingService iThrowingService;

    @Autowired
    private IFeedingService iFeedingService;




    @RequestMapping("/code")
    public Map getCodeDetails(@RequestParam("code")String code){
        Map parameterMap = new HashMap();
        Map returnMap = new HashMap();
        parameterMap.put("qrCode",code);
        ProCode proCode = iCodeService.getCodeDetails(parameterMap);
        if(null == proCode){
            returnMap.put(CommonConstants.http_status,CommonConstants.filed);
            returnMap.put(CommonConstants.http_message,"码不存在！");
        }else{
            parameterMap.clear();
            //卷包工单查询
            parameterMap.put("produceDate",proCode.getProduceDate());
            parameterMap.put("machineCode",proCode.getMachineCode());

            WrapOrder wrapOrder = wrapService.getWrapOrder(parameterMap);
            proCode.setWrapOrder(wrapOrder);
            ///根据喂丝机关系找到制丝批次信息,考虑到风力送丝到卷包有时间差，所以预留
            parameterMap.put("timeBreak",timeBreak);
            ///查询喂丝机记录
            List<FeedingRecord> feedingRecords = iFeedingService.getFeedingRecord(parameterMap);

            List<Throwing> throwingList  = iThrowingService.getThrowingDetails(parameterMap);
            proCode.setThrowing(throwingList);





        }





        return returnMap;



    }







}
