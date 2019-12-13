package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.entity.FeedingRecord;
import com.hlxd.microcloud.entity.ProCode;
import com.hlxd.microcloud.entity.Throwing;
import com.hlxd.microcloud.service.ICodeService;
import com.hlxd.microcloud.service.IFeedingService;
import com.hlxd.microcloud.service.IThrowingService;
import com.hlxd.microcloud.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 喂丝控制器
 */
@RestController
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private IFeedingService iFeedingService;

    @Autowired
    private IThrowingService iThrowingService;

    @Autowired
    private ICodeService iCodeService;

    /**
     * 根据投丝机号和时间查码
     * @param feedTime
     * @param feedingMachineCode
     * @return
     *
     */
    @RequestMapping("/selectByFeed")
    public Map selectByFeed(String feedTime,String feedingMachineCode){
        Map returnMap = new HashMap();
        if (feedTime!=null && feedTime!="" && feedingMachineCode!=null && feedingMachineCode!=""){

            FeedingRecord feedingRecord = new FeedingRecord();
            //根据投丝机号和时间获取制丝号
            feedingRecord = iFeedingService.selectByFeed(feedTime,feedingMachineCode);
            if(feedingRecord==null || "".equals(feedingRecord)){
                returnMap.put(CommonConstants.http_message,"码不存在");
            }else {
                Throwing throwing = new Throwing();
                //根据制丝号获取制丝时间
                throwing = iThrowingService.getThrowByOrder(feedingRecord.getThrowingNumber());
                if(throwing!=null){
                    //根据时间范围取码
                    List<ProCode> proCodes = iCodeService.getCodeByTime(throwing.getDoBeginDate(),throwing.getDoEndDate());
                    returnMap.put(CommonConstants.http_data,proCodes);
                }else returnMap.put(CommonConstants.http_message,"码不存在");
            }

        }else returnMap.put(CommonConstants.http_message,"参数为空");

        return returnMap;
    }
}
