package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.entity.MaterConsyme;
import com.hlxd.microcloud.entity.ProCode;
import com.hlxd.microcloud.entity.Throwing;
import com.hlxd.microcloud.entity.WrapOrder;
import com.hlxd.microcloud.service.ICodeService;
import com.hlxd.microcloud.service.IThrowingService;
import com.hlxd.microcloud.service.MaterConsymeService;
import com.hlxd.microcloud.service.WrapService;
import com.hlxd.microcloud.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.Configuration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mater")
public class MaterConsumeControlelr {

    @Autowired
    private MaterConsymeService materConsymeService;

    @Autowired
    private WrapService wrapService;

    @Autowired
    private IThrowingService iThrowingService;

    @Autowired
    private ICodeService iCodeService;

    /**
     * 根据物料查码
     * @param materialCode
     * @return
     */
    @RequestMapping("/selectByMaterVode")
    public Map selectByMaterVode(String materialCode){
        Map returnMap = new HashMap();
        List<ProCode> list = new ArrayList<>();
        if(materialCode!=null || materialCode!=""){
            //根据物料代码获取对象信息
            List<MaterConsyme> materConsymes = materConsymeService.selectByMaterCode(materialCode);
            //迭代物料对象信息取出制丝或卷包批次号
            for (MaterConsyme mater:materConsymes){
                //根据工单号或者制丝号获取对象信息
                WrapOrder wrapOrder = wrapService.selectByNumber(mater.getBatchNumber());
                Throwing throwing = iThrowingService.getThrowByOrder(mater.getBatchNumber());
                if (wrapOrder==null && throwing==null){
                    returnMap.put(CommonConstants.http_message,"码不存在");
                }else {
                    if (wrapOrder!=null){
                        //根据卷包机台，时间获取码
                        List<ProCode> proCodes = iCodeService.getCodeByWrap(wrapOrder.getMachineCode(),wrapOrder.getDoBeginDate(),wrapOrder.getDoEndDate());
                        if (proCodes.size()>0){
                            list.addAll(proCodes);
                        }
                    }
                    if (throwing!=null){
                        List<ProCode> proCodes1 = iCodeService.getCodeByTime(throwing.getDoBeginDate(),throwing.getDoEndDate());
                        if (proCodes1.size()>0){
                            list.addAll(proCodes1);
                        }
                    }
                }
            }
        }else returnMap.put(CommonConstants.http_message,"参数为空");

        returnMap.put(CommonConstants.http_data,list);

        return returnMap;
    }

}
