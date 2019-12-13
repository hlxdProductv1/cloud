package com.hlxd.microcloud.controller;


import com.hlxd.microcloud.entity.ProCode;
import com.hlxd.microcloud.entity.WrapOrder;
import com.hlxd.microcloud.service.ICodeService;
import com.hlxd.microcloud.service.WrapService;
import com.hlxd.microcloud.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 卷包工单控制器
 */
@RestController
@RequestMapping("/wrap")
public class WarpOrderController {

    @Autowired
    private WrapService wrapService;

    @Autowired
    private ICodeService iCodeService;

    /**
     * 按卷包工单查码
     * @param machineCode
     * @param produceDate
     * @param classId
     * @return
     */
    @RequestMapping("/selectByWrapOrder")
    public Map selectByWrapOrder(String machineCode,String produceDate,String classId){
        Map returnMap = new HashMap();
        List<ProCode> proCodes =new ArrayList<>();
        if(machineCode!=""&& machineCode!=null && produceDate!="" && produceDate!=null && classId!=""&&classId!=null){
            //工单查询
            List<WrapOrder> wrapOrders = wrapService.selectByWrapOrder(machineCode,produceDate,classId);
            if(wrapOrders.isEmpty() || wrapOrders.size()==0){
                returnMap.put(CommonConstants.http_message,"码不存在！");
            }else {
                for (WrapOrder wrapOrder:wrapOrders){
                    //根据工单信息查码
                    List<ProCode> proCodes1= iCodeService.getCodeByWrap(machineCode,wrapOrder.getDoBeginDate(),wrapOrder.getDoEndDate());
                    proCodes.addAll(proCodes1);
                }
                returnMap.put(CommonConstants.http_data,proCodes);
            }

        }else {
            returnMap.put(CommonConstants.http_message,"参数为空");
        }


        return  returnMap;
    }

}
