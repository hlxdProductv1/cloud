package com.hlxd.microcloud.controller;


import com.hlxd.microcloud.entity.ProCode;
import com.hlxd.microcloud.entity.Throwing;
import com.hlxd.microcloud.service.ICodeService;
import com.hlxd.microcloud.service.IThrowingService;
import com.hlxd.microcloud.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 制丝控制器
 */
@RestController
@RequestMapping("/throw")
public class ThrowController {

    @Autowired
    private IThrowingService iThrowingService;

    @Autowired
    private ICodeService iCodeService;

    /**
     * 根据制丝号取码
     * @param throwingNumber
     * @return
     */
    @RequestMapping("/selectByNumber")
    public Map selectByNumber(String throwingNumber){
        Map returnMap = new HashMap();
        if (throwingNumber!=null && throwingNumber!=""){
            //根据制丝号获取时间
            Throwing throwing = iThrowingService.getThrowByOrder(throwingNumber);
            if (throwing!=null){
                //根据时间范围取码
                List<ProCode> proCodes = iCodeService.getCodeByTime(throwing.getDoBeginDate(),throwing.getDoEndDate());
                returnMap.put(CommonConstants.http_data,proCodes);

            }else returnMap.put(CommonConstants.http_message,"码不存在");

        }else returnMap.put(CommonConstants.http_message,"参数为空");

        return  returnMap;
    }
}
