package com.hlxd.microcloud.config;


import com.hlxd.microcloud.entity.*;
import com.hlxd.microcloud.service.*;
import com.hlxd.microcloud.util.DruidUtils;
import com.hlxd.microcloud.util.ThreadPoolManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.acl.LastOwnerException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/12/1011:00
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Component
@Slf4j
public class Uploadconfig {


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

    /**
     * 数据同步
     *
     * **/
    @Scheduled(cron = "0 0 0 ? * * ")
    public void uploadMethod() throws ParseException, SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ThreadPoolManager threadPoolManager = ThreadPoolManager.newInstance();//线程池管理
        Date startDate = new Date();
        String date = simpleDateFormat.format(startDate);
        Statement st = null;
        ResultSet rs = null;
        Connection sqlServer = null;
        boolean f = false;
        List<WrapOrder> wrapOrders = new ArrayList<>();
        List<Reject> rejects = new ArrayList<>();//剔除统计
        List<MaterialConsume> materialConsumes = new ArrayList<>();//辅料消耗
        List<MaterialConsumeDetails> materialConsumeDetails = new ArrayList<>();//辅料消耗详情
        List<Verification> verifications = new ArrayList<>();//检测记录
        List<VerificationDetails> verificationDetails = new ArrayList<>();//检测记录详情
        List<Throwing> throwingList = new ArrayList<>();//制丝工单
        List<Blending> blendings = new ArrayList<>();//投料信息
        List<BlendingDetails> blendingDetails = new ArrayList<>();//投料详情
        List<FeedingRecord> feedingRecords = new ArrayList<>();
        List<Logistics> logistics = new ArrayList<>();
        String planType = "卷包";
        try{
            sqlServer = DruidUtils.getConnection();
            st = sqlServer.createStatement();
            f =st.execute("select * from wrapsOrder where str_to_date(produceDate,'yyyy-MM-dd')="+date);//卷包工单
            if(f){
                rs = st.getResultSet();
                if(null != rs ){
                    ResultSetMetaData resultSetMetaData = rs.getMetaData();
                    while (rs.next()){
                        WrapOrder wrapOrder = new WrapOrder();
                        for(int i=1;i<resultSetMetaData.getColumnCount();i++){
                            String columnName = resultSetMetaData.getColumnName(i);
                            String value = rs.getString(i);
                            switch (columnName){
                                case "wrapsNumber": wrapOrder.setWrapsNumber(value);
                                    break;case "equipment_code": wrapOrder.setMachineCode(value);
                                    break;case "produce_date": wrapOrder.setProduceDate(value);
                                    break;case "shiftId": wrapOrder.setClassId(value);
                                    break;case "brand_code": wrapOrder.setBrandCode(value);
                                    break;case "plan_begin_date": wrapOrder.setPlanBeginDate(value);
                                    break;case "plan_end_date": wrapOrder.setPlanEndDate(value);
                                    break;case "plan_quantity": wrapOrder.setPlanQuantity(value);
                                    break;case "do_begin_date": wrapOrder.setDoBeginDate(value);
                                    break;case "do_end_date": wrapOrder.setDoEndDate(value);
                                    break;case "do_quantity": wrapOrder.setDoQuantity(value);
                                    break;case "remark": wrapOrder.setRemark(value);
                                    break;default: break;
                            }
                        }
                        wrapOrder.setId(UUID.randomUUID().toString());
                        wrapOrders.add(wrapOrder);
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
                }
            }
            //剔除统计
            f =st.execute("select * from reject where str_to_date(produceDate,'yyyy-MM-dd')="+date);//剔除统计
            rs = null;
            if(f){
                rs = st.getResultSet();
                if(null != rs ){
                    ResultSetMetaData resultSetMetaData = rs.getMetaData();
                    while (rs.next()){
                        Reject reject = new Reject();
                        for(int i=1;i<resultSetMetaData.getColumnCount();i++){
                            String columnName = resultSetMetaData.getColumnName(i);
                            String value = rs.getString(i);
                            switch (columnName){
                                case "produce_date": reject.setRejectDate(value);
                                    break;case "machine_name": reject.setRejectType(value=="包装机"?"2":"1");
                                    break;case "machine_code": reject.setMachineCode(value);
                                    break;case "reject_reason": reject.setRejectReason(value);
                                    break;default: break;
                            }
                        }
                        reject.setId(UUID.randomUUID().toString());
                        rejects.add(reject);
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
                }
            }
            //辅料消耗统计
            f =st.execute("select * from materialConsume where str_to_date(produceDate,'yyyy-MM-dd')="+date);//辅料消耗统计
            rs = null;
            if(f){
                rs = st.getResultSet();
                if(null != rs ){
                    ResultSetMetaData resultSetMetaData = rs.getMetaData();
                    Map consumeMap  = new HashMap();
                    while (rs.next()){
                        MaterialConsume materialConsume = new MaterialConsume();
                        MaterialConsumeDetails materialConsumeDetails1 = new MaterialConsumeDetails();
                        for(int i=1;i<resultSetMetaData.getColumnCount();i++){
                            String columnName = resultSetMetaData.getColumnName(i);
                            switch (columnName){
                                case "material_code":materialConsume.setMaterialCode(rs.getString(i));
                                    break;case "logistics_number": materialConsume.setLogisticsNumber(rs.getString(i));
                                    break;case "unit_name": materialConsume.setUnitName(rs.getString(i));
                                    break;case "do_use": materialConsume.setDoUse(rs.getString(i));
                                    break;case "do_unit_name": materialConsume.setDoUnitName(rs.getString(i));
                                    break;case "remark": materialConsume.setRemark(rs.getString(i));
                                    break;case "batch_number": materialConsume.setBatchNumber(rs.getString(i));
                                    break;case "box_code": materialConsumeDetails1.setBoxCode(rs.getString(i));
                                    break;case "begin_date": materialConsumeDetails1.setBeginDate(rs.getString(i));
                                    break;case "end_date": materialConsumeDetails1.setEndDate(rs.getString(i));
                                    break;case "produce_date": materialConsumeDetails1.setProduceDate(rs.getString(i));
                                    break;case "purchase_number": materialConsumeDetails1.setPurchaseNumber(rs.getString(i));
                                    break;case "supplier": materialConsumeDetails1.setSupplier(rs.getString(i));
                                    break;case "d_remark": materialConsumeDetails1.setRemark(rs.getString(i));
                                    break;default: break;
                            }
                        }
                        String consumerId = UUID.randomUUID().toString();
                        materialConsume.setId(consumerId);
                        materialConsumeDetails1.setId(UUID.randomUUID().toString());
                        boolean flag = false;
                        for(MaterialConsume materialConsume1:materialConsumes){
                            if(materialConsume1.getMaterialCode()==materialConsume.getMaterialCode()){
                                flag=true;
                                consumerId =materialConsume1.getId();
                            }
                        }
                        if(!flag){
                            materialConsumes.add(materialConsume);
                        }
                        materialConsumeDetails1.setConsumeId(consumerId);
                        materialConsumeDetails.add(materialConsumeDetails1);
                    }
                    int consumeNum = (int) Math.floor(materialConsumes.size()/50);
                    int consumeDetailsNum = (int) Math.floor(materialConsumes.size()/50);
                    for(int i=0;i<=consumeNum;i++){
                        final int tem = i;
                        threadPoolManager.addExecuteTask(new Runnable() {
                            @Override
                            public void run() {
                                List<MaterialConsume> materialConsumeList = materialConsumes.subList(tem*50,(tem+1)*50>materialConsumes.size()?materialConsumes.size():(tem+1)*50);
                                if(materialConsumeList.size()>0){
                                    iMaterialConsumeService.batchInsert(materialConsumeList);
                                }
                            }
                        });
                    }
                    for(int i=0;i<=consumeDetailsNum;i++){
                        final int tem = i;
                        threadPoolManager.addExecuteTask(new Runnable() {
                            @Override
                            public void run() {
                                List<MaterialConsumeDetails> materialConsumeDetailsList = materialConsumeDetails.subList(tem*50,(tem+1)*50>materialConsumeDetails.size()?materialConsumeDetails.size():(tem+1)*50);
                                if(materialConsumeDetailsList.size()>0){
                                    iMaterialConsumeService.batchInsertDetails(materialConsumeDetailsList);
                                }
                            }
                        });
                    }
                }
            }
            //检查记录
            f = st.execute("select * from verification where str_to_date(produceDate,'yyyy-MM-dd')="+date); //检查记录
            rs = null;
            if(f){
                rs = st.getResultSet();
                if(null != rs ){
                    ResultSetMetaData resultSetMetaData = rs.getMetaData();
                    while (rs.next()){
                        Verification verification = new Verification();
                        VerificationDetails verificationDetails1 = new VerificationDetails();
                        for(int i=1;i<resultSetMetaData.getColumnCount();i++){
                            String columnName = resultSetMetaData.getColumnName(i);
                            switch (columnName){
                                case "verification_number":
                                    verification.setVerificationNumber(rs.getString(i));
                                    break;case "verification_type": verification.setVerificationType(rs.getString(i));
                                    break;case "simple_date": verification.setSimpleDate(rs.getString(i));
                                    break;case "simple_count": verification.setSimpleCount(rs.getString(i));
                                    break;case "score": verification.setScore(rs.getString(i));
                                    break;case "verification_people": verification.setVerificationPeople(rs.getString(i));
                                    break;case "remark": verification.setRemark(rs.getString(i));
                                    break;case "batch_number": verification.setBatchNumber(rs.getString(i));
                                    break;case "item_code": verificationDetails1.setItemCode(rs.getString(i));
                                    break;case "average": verificationDetails1.setAverage(rs.getString(i));
                                    break;case "simple_max": verificationDetails1.setSimpleMax(rs.getString(i));
                                    break;case "simple_min": verificationDetails1.setSimpleMin(rs.getString(i));
                                    break;case "cpk": verificationDetails1.setCpk(rs.getString(i));
                                    break;case "cv": verificationDetails1.setCv(rs.getString(i));
                                    break;case "match_rate": verificationDetails1.setMatchRate(rs.getString(i));
                                    break;default: break;
                            }
                        }
                        String verificationId = UUID.randomUUID().toString();
                        verification.setId(verificationId);
                        verificationDetails1.setId(UUID.randomUUID().toString());
                        verificationDetails1.setVerificationId(verificationId);
                        verificationDetails.add(verificationDetails1);
                        for(Verification verification1:verifications){
                            if(verification1.getVerificationNumber()!=verification.getVerificationNumber()){
                                verifications.add(verification);
                            }
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
                }
            }
            //投料记录
            f = st.execute("select * from blending where str_to_date(produceDate,'yyyy-MM-dd')="+date); //投料记录
            rs = null;
            if(f){
                rs = st.getResultSet();
                if(null != rs ){
                    ResultSetMetaData resultSetMetaData = rs.getMetaData();
                    while (rs.next()){
                        Blending blending = new Blending();
                        BlendingDetails blendingDetails1 = new BlendingDetails();
                        for(int i=1;i<resultSetMetaData.getColumnCount();i++){
                            String columnName = resultSetMetaData.getColumnName(i);
                            switch (columnName){
                                case "blending_date":blending.setBlendingDate(rs.getString(i));
                                    break;case "blending_number": blending.setBlendingNumber(rs.getString(i));
                                    break;case "line_code": blending.setLineCode(rs.getString(i));
                                    break;case "brand_code": blending.setBrandCode(rs.getString(i));
                                    break;case "blending_formulation": blending.setBlendingFormulation(rs.getString(i));
                                    break;case "plan_blending_quantity": blending.setPlanBlendingQuantity(rs.getString(i));
                                    break;case "plan_blending_weight": blending.setPlanBlendingWeight(rs.getString(i));
                                    break;case "do_blending_quantity": blending.setDoBlendingQuantity(rs.getString(i));
                                    break;case "do_blending_weight": blending.setDoBlendingWeight(rs.getString(i));
                                    break;case "blending_status": blending.setBlendingStatus(rs.getString(i));
                                    break;case "remark": blending.setRemark(rs.getString(i));
                                    break;case "box_code": blendingDetails1.setBoxCode(rs.getString(i));
                                    break;case "d_blending_date": blendingDetails1.setBlendingDate(rs.getString(i));
                                    break;case "tobacco_year": blendingDetails1.setTobaccoYear(rs.getString(i));
                                    break;case "produce_area": blendingDetails1.setProduceArea(rs.getString(i));
                                    break;case "breed": blendingDetails1.setBreed(rs.getString(i));
                                    break;case "level": blendingDetails1.setLevel(rs.getString(i));
                                    break;case "gross_weight": blendingDetails1.setGrossWeight(rs.getString(i));
                                    break;case "net_weight": blendingDetails1.setNetWeight(rs.getString(i));
                                    break;case "redry_number": blendingDetails1.setRedryNumber(rs.getString(i));
                                    break;case "d_remark": blendingDetails1.setRemark(rs.getString(i));
                                    break;default: break;
                            }
                        }
                        blending.setId(UUID.randomUUID().toString());
                        blendingDetails1.setId(UUID.randomUUID().toString());
                        boolean flag = false;
                        for(Blending blending1:blendings){
                            if(blending1.getBlendingNumber()==blending.getBlendingNumber()){
                                flag=true;
                            }
                        }
                        if(!flag){
                            blendings.add(blending);
                        }
                        blendingDetails1.setBlendingNumber(blending.getBlendingNumber());
                        blendingDetails.add(blendingDetails1);
                    }
                    int blendingNum = (int) Math.floor(blendings.size()/50);
                    int blendingDetailsNum = (int) Math.floor(blendingDetails.size()/50);
                    for(int i=0;i<=blendingNum;i++){
                        final int tem = i;
                        threadPoolManager.addExecuteTask(new Runnable() {
                            @Override
                            public void run() {
                                List<Blending> blendingList = blendings.subList(tem*50,(tem+1)*50>blendings.size()?blendings.size():(tem+1)*50);
                                if(blendingList.size()>0){
                                    blendingService.batchInsert(blendingList);
                                }
                            }
                        });
                    }
                    for(int i=0;i<=blendingDetailsNum;i++){
                        final int tem = i;
                        threadPoolManager.addExecuteTask(new Runnable() {
                            @Override
                            public void run() {
                                List<BlendingDetails> blendingDetailsList = blendingDetails.subList(tem*50,(tem+1)*50>blendingDetails.size()?blendingDetails.size():(tem+1)*50);
                                if(blendingDetailsList.size()>0){
                                    blendingService.batchInsertDetails(blendingDetailsList);
                                }
                            }
                        });
                    }
                }
            }
            //制丝记录
            f = st.execute("select * from throwing where str_to_date(produceDate,'yyyy-MM-dd')="+date);//制丝记录
            rs = null;
            if(f){
                rs = st.getResultSet();
                if(null != rs ){
                    ResultSetMetaData resultSetMetaData = rs.getMetaData();
                    while (rs.next()){
                        Throwing throwing = new Throwing();
                        for(int i=1;i<resultSetMetaData.getColumnCount();i++){
                            String columnName = resultSetMetaData.getColumnName(i);
                            switch (columnName){
                                case "throwing_number":throwing.setThrowingNumber(rs.getString(i));
                                    break;case "plan_begin_date": throwing.setPlanBeginDate(rs.getString(i));
                                    break;case "plan_end_date": throwing.setPlanEndDate(rs.getString(i));
                                    break;case "do_begin_date": throwing.setDoBeginDate(rs.getString(i));
                                    break;case "do_end_date": throwing.setDoEndDate(rs.getString(i));
                                    break;case "blending_weight": throwing.setBlendingWeight(rs.getString(i));
                                    break;case "product_weight": throwing.setProductWeight(rs.getString(i));
                                    break;case "line_code": throwing.setLineCode(rs.getString(i));
                                    break;case "section_id": throwing.setSectionCode(rs.getString(i));
                                    break;case "class_id": throwing.setClassId(rs.getString(i));
                                    break;case "blending_number": throwing.setBlendingNumber(rs.getString(i));
                                    break;case "remark": throwing.setRemark(rs.getString(i));
                                    break;default: break;
                            }
                        }
                        throwing.setId(UUID.randomUUID().toString());
                        throwingList.add(throwing);
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
                }
            }
            //喂丝记录
            f = st.execute("select * from feeding_record where str_to_date(produceDate,'yyyy-MM-dd')="+date); //喂丝记录
            rs = null;
            if(f){
                rs = st.getResultSet();
                if(null != rs ){
                    ResultSetMetaData resultSetMetaData = rs.getMetaData();
                    while (rs.next()){
                        FeedingRecord feedingRecord = new FeedingRecord();
                        for(int i=1;i<resultSetMetaData.getColumnCount();i++){
                            String columnName = resultSetMetaData.getColumnName(i);
                            switch (columnName){
                                case "throwing_number":
                                    feedingRecord.setThrowingNumber(rs.getString(i));
                                    break;case "start_feeding_date": feedingRecord.setStartFeedingDate(rs.getString(i));
                                    break;case "end_feeding_date": feedingRecord.setEndFeedingDate(rs.getString(i));
                                    break;case "equipment_code": feedingRecord.setMachineCode(rs.getString(i));
                                    break;case "feeding_equipment_code": feedingRecord.setFeedingMachineCode(rs.getString(i));
                                    break;case "remark": feedingRecord.setRemark(rs.getString(i));
                                    break;case "organize_code": feedingRecord.setOrganizeCode(rs.getString(i));
                                    break;default: break;
                            }
                        }
                        feedingRecord.setId(UUID.randomUUID().toString());
                        feedingRecords.add(feedingRecord);
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
                }
            }
            //入库记录
            f = st.execute("select * from logistics where str_to_date(produceDate,'yyyy-MM-dd')="+date); //喂丝记录
            rs = null;
            if(f){
                rs = st.getResultSet();
                if(null != rs ){
                    ResultSetMetaData resultSetMetaData = rs.getMetaData();
                    while (rs.next()){
                        Logistics logistics1 = new Logistics();
                        for(int i=1;i<resultSetMetaData.getColumnCount();i++){
                            String columnName = resultSetMetaData.getColumnName(i);
                            switch (columnName){
                                case "qr_code":
                                    logistics1.setQrCode(rs.getString(i));
                                    break;case "in_house_date": logistics1.setInHouseDate(rs.getString(i));
                                    break;case "out_house_date": logistics1.setOutHouseDate(rs.getString(i));
                                    break;default: break;
                            }
                        }
                        logistics1.setId(UUID.randomUUID().toString());
                        logistics.add(logistics1);
                    }
                    int num = (int) Math.floor(logistics.size()/50);
                    for(int i=0;i<=num;i++){
                        int number = i;
                        threadPoolManager.addExecuteTask(new Runnable() {
                            @Override
                            public void run() {
                                List<Logistics> logisticsList = logistics.subList(number*50,(number+1)*50>logistics.size()?logistics.size():(number+1)*50);
                                if(logisticsList.size()>0){
                                    iLogisticsService.batchInsert(logisticsList);
                                }
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            Log log = new Log();
            log.setId(UUID.randomUUID().toString());
            log.setFaultTime(date);
            log.setMessage(e.getMessage());
            iLogService.insertLog(log);
            e.printStackTrace();
        }finally{
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (sqlServer != null) {
                sqlServer.close();
            }
        }

    }
}
