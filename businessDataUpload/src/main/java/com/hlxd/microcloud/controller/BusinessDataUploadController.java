package com.hlxd.microcloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.hlxd.microcloud.entity.*;
import com.hlxd.microcloud.service.*;
import com.hlxd.microcloud.util.CommonConstants;
import com.hlxd.microcloud.util.ThreadPoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/12/1217:16
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@RestController
@RequestMapping("/apiData")
public class BusinessDataUploadController {

    @Autowired
    private WrapService wrapService;

    @Autowired
    private RejectService rejectService;

    @Autowired
    private IMaterialConsumeService iMaterialConsumeService;


    @Autowired
    private IVerificationService iVerificationService;


    @Autowired
    private BlendingService blendingService;

    @Autowired
    private IThrowingService iThrowingService;

    @Autowired
    private IFeedingService iFeedingService;

    @Autowired
    private ILogisticsService iLogisticsService;

    @Autowired
    private ILogService iLogService;



    private static final ThreadPoolManager threadPoolManager = ThreadPoolManager.newInstance();



    @RequestMapping("/blending")
    public Map getBlending(@RequestBody String s){
        Map returnMap = new HashMap();
        List<Blending> blendings = JSONObject.parseArray(s,Blending.class);
        List<BlendingDetails> blendingDetailsList = new ArrayList<>();
        for(Blending blending:blendings){
            blending.setId(UUID.randomUUID().toString());
            for(BlendingDetails blendingDetails:blendingDetailsList){
                blendingDetails.setId(UUID.randomUUID().toString());
            }
            blendingDetailsList.addAll(blending.getBlendingDetails());
        }
        int blendingNumber = (int) Math.floor(blendings.size()/50);
        for(int i=0;i<=blendingNumber;i++){
            final int tag = i;
            threadPoolManager.addExecuteTask(new Runnable() {
                @Override
                public void run() {
                    List<Blending> blendingList = blendings.subList(tag*50,(tag+1)*50>blendings.size()?blendings.size():(tag+1)*50);
                    if(blendingList.size()>0){
                        blendingService.batchInsert(blendingList);
                    }
                }
            });
        }
        int blendingDetailsNum = (int) Math.floor(blendingDetailsList.size()/50);
        for(int i=0;i<=blendingDetailsNum;i++){
            final int tem = i;
            threadPoolManager.addExecuteTask(new Runnable() {
                @Override
                public void run() {
                    List<BlendingDetails> blendingDetails = blendingDetailsList.subList(tem*50,(tem+1)*50>blendingDetailsList.size()?blendingDetailsList.size():(tem+1)*50);
                    if(blendingDetails.size()>0){
                        blendingService.batchInsertDetails(blendingDetails);
                    }
                }
            });
        }
        returnMap.put(CommonConstants.http_status,CommonConstants.success);
        returnMap.put(CommonConstants.http_message,CommonConstants.http_success);
        return returnMap;
    }

    @RequestMapping("/feedingRecord")
    public  Map getFeedingRecord(@RequestBody String s){
        Map returnMap = new HashMap();
        List<FeedingRecord> feedingRecords = JSONObject.parseArray(s,FeedingRecord.class);
        for(FeedingRecord feedingRecord:feedingRecords){
            feedingRecord.setId(UUID.randomUUID().toString());
        }
        int num = (int) Math.floor(feedingRecords.size()/50);
        for(int i=0;i<=num;i++){
            int number = i;
            threadPoolManager.addExecuteTask(new Runnable() {
                @Override
                public void run() {
                    List<FeedingRecord> feedingRecordList = feedingRecords.subList(number*50,(number+1)*50>feedingRecords.size()?feedingRecords.size():(number+1)*50);
                    if(feedingRecordList.size()>0){
                        iFeedingService.batchInsert(feedingRecordList);
                    }
                }
            });
        }
        returnMap.put(CommonConstants.http_status,CommonConstants.success);
        returnMap.put(CommonConstants.http_message,CommonConstants.http_success);
        return returnMap;
    }

    @RequestMapping("/logistics")
    public Map getLogistics(@RequestBody String s){
        Map returnMap = new HashMap();
        List<Logistics> logisticsList = JSONObject.parseArray(s,Logistics.class);
        for(Logistics logistics:logisticsList){
            logistics.setId(UUID.randomUUID().toString());
        }
        int num = (int) Math.floor(logisticsList.size()/50);
        for(int i=0;i<=num;i++){
            int number = i;
            threadPoolManager.addExecuteTask(new Runnable() {
                @Override
                public void run() {
                    List<Logistics> logistics = logisticsList.subList(number*50,(number+1)*50>logisticsList.size()?logisticsList.size():(number+1)*50);
                    if(logistics.size()>0){
                        iLogisticsService.batchInsert(logistics);
                    }
                }
            });
        }
        returnMap.put(CommonConstants.http_status,CommonConstants.success);
        returnMap.put(CommonConstants.http_message,CommonConstants.http_success);
        return returnMap;
    }

    @RequestMapping("/materialConsume")
    public Map getMaterialConsume(@RequestBody String s){
        Map returnMap = new HashMap();
        List<MaterialConsume> materialConsumeList = JSONObject.parseArray(s,MaterialConsume.class);
        List<MaterialConsumeDetails> materialConsumeDetailsList = new ArrayList<>();
        for(MaterialConsume materialConsume:materialConsumeList){
            materialConsume.setId(UUID.randomUUID().toString());
            List<MaterialConsumeDetails> materialConsumeDetails = materialConsume.getMaterialConsumeDetails();
            for(MaterialConsumeDetails materialConsumeDetails1:materialConsumeDetails){
                materialConsumeDetails1.setId(UUID.randomUUID().toString());
                materialConsumeDetails1.setConsumeId(materialConsume.getId());
            }
            materialConsumeDetailsList.addAll(materialConsumeDetails);
        }

        int consumeNum = (int) Math.floor(materialConsumeList.size()/50);
        int consumeDetailsNum = (int) Math.floor(materialConsumeDetailsList.size()/50);
        for(int i=0;i<=consumeNum;i++){
            final int tem = i;
            threadPoolManager.addExecuteTask(new Runnable() {
                @Override
                public void run() {
                    List<MaterialConsume> materialConsumes = materialConsumeList.subList(tem*50,(tem+1)*50>materialConsumeList.size()?materialConsumeList.size():(tem+1)*50);
                    if(materialConsumes.size()>0){
                        iMaterialConsumeService.batchInsert(materialConsumes);
                    }
                }
            });
        }
        for(int i=0;i<=consumeDetailsNum;i++){
            final int tem = i;
            threadPoolManager.addExecuteTask(new Runnable() {
                @Override
                public void run() {
                    List<MaterialConsumeDetails> materialConsumeDetails = materialConsumeDetailsList.subList(tem*50,(tem+1)*50>materialConsumeDetailsList.size()?materialConsumeDetailsList.size():(tem+1)*50);
                    if(materialConsumeDetails.size()>0){
                        iMaterialConsumeService.batchInsertDetails(materialConsumeDetails);
                    }
                }
            });
        }
        returnMap.put(CommonConstants.http_status,CommonConstants.success);
        returnMap.put(CommonConstants.http_message,CommonConstants.http_success);
        return returnMap;
    }

    @RequestMapping("/reject")
    public Map getReject(@RequestBody String s){
        Map returnMap = new HashMap();
        List<Reject> rejects = JSONObject.parseArray(s,Reject.class);
        for(Reject reject:rejects){
            reject.setId(UUID.randomUUID().toString());
        }
        int num = (int) Math.floor(rejects.size()/50);
        for(int i=0;i<=num;i++){
            final int tem = i;
            threadPoolManager.addExecuteTask(new Runnable() {
                @Override
                public void run() {
                    List<Reject> rejectList = rejects.subList(tem*50,(tem+1)*50>rejects.size()?rejects.size():(tem+1)*50);
                    if(rejectList.size()>0){
                        rejectService.batchInsert(rejectList);
                    }
                }
            });
        }
        returnMap.put(CommonConstants.http_status,CommonConstants.success);
        returnMap.put(CommonConstants.http_message,CommonConstants.http_success);
        return returnMap;
    }

    @RequestMapping("/throwing")
    public Map getThrowing(@RequestBody String s){
        Map returnMap = new HashMap();
        List<Throwing> throwingList = JSONObject.parseArray(s,Throwing.class);
        for(Throwing throwing:throwingList){
            throwing.setId(UUID.randomUUID().toString());
        }
        int num = (int) Math.floor(throwingList.size()/50);
        for(int i=0;i<=num;i++){
            int number = i;
            threadPoolManager.addExecuteTask(new Runnable() {
                @Override
                public void run() {
                    List<Throwing> throwings = throwingList.subList(number*50,(number+1)*50>throwingList.size()?throwingList.size():(number+1)*50);
                    if(throwings.size()>0){
                        iThrowingService.batchInsert(throwings);
                    }
                }
            });
        }
        returnMap.put(CommonConstants.http_status,CommonConstants.success);
        returnMap.put(CommonConstants.http_message,CommonConstants.http_success);
        return returnMap;
    }

    @RequestMapping("/verification")
    public Map getVerification(@RequestBody String s){
        Map returnMap = new HashMap();
        List<Verification> verifications = JSONObject.parseArray(s,Verification.class);
        List<VerificationDetails> verificationDetails = new ArrayList<>();
        for(Verification verification:verifications){
            verification.setId(UUID.randomUUID().toString());
            List<VerificationDetails> verificationDetailsList = verification.getVerificationDetails();
            for(VerificationDetails verificationDetails1:verificationDetails){
                verificationDetails1.setId(UUID.randomUUID().toString());
                verificationDetails1.setVerificationId(verification.getId());
            }
        }
        int verificationNum = (int) Math.floor(verifications.size()/50);
        int verificationDetailsNum = (int) Math.floor(verificationDetails.size()/50);
        for(int i=0;i<=verificationNum;i++){
            final int tem = i;
            threadPoolManager.addExecuteTask(new Runnable() {
                @Override
                public void run() {
                    List<Verification> verificationList = verifications.subList(tem*50,(tem+1)*50>verifications.size()?verifications.size():(tem+1)*50);
                    if(verificationList.size()>0){
                        iVerificationService.batchInsert(verificationList);
                    }
                }
            });
        }
        for(int i=0;i<=verificationDetailsNum;i++){
            final int tem = i;
            threadPoolManager.addExecuteTask(new Runnable() {
                @Override
                public void run() {
                    List<VerificationDetails> verificationDetailsList = verificationDetails.subList(tem*50,(tem+1)*50>verificationDetails.size()?verificationDetails.size():(tem+1)*50);
                    if(verificationDetailsList.size()>0){
                        iVerificationService.batchInsertDetails(verificationDetailsList);
                    }
                }
            });
        }
        returnMap.put(CommonConstants.http_status,CommonConstants.success);
        returnMap.put(CommonConstants.http_message,CommonConstants.http_success);
        return returnMap;
    }

    @RequestMapping("/wrapsOrder")
    public Map getWrapsOrder(@RequestBody String s){
        Map returnMap = new HashMap();
        List<WrapOrder> wrapOrders = JSONObject.parseArray(s,WrapOrder.class);
        for(WrapOrder wrapOrder:wrapOrders){
            wrapOrder.setId(UUID.randomUUID().toString());
        }
        int num = (int) Math.floor(wrapOrders.size()/50);
        for(int i=0;i<=num;i++){
            final int tem = i;
            threadPoolManager.addExecuteTask(new Runnable() {
                @Override
                public void run() {
                    List<WrapOrder> wrapOrderList = wrapOrders.subList(tem*50,(tem+1)*50>wrapOrders.size()?wrapOrders.size():(tem+1)*50);
                    if(wrapOrderList.size()>0){
                        wrapService.batchInsert(wrapOrderList);
                    }
                }
            });
        }
        returnMap.put(CommonConstants.http_status,CommonConstants.success);
        returnMap.put(CommonConstants.http_message,CommonConstants.http_success);
        return returnMap;
    }






}
