package com.hlxd.microcloud.controller;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.entity.vo.RealtimeDataVo;
import com.hlxd.microcloud.service.RealtimeDataService;

/***
 * - 实时数据  前端控制器
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
@RestController
@RequestMapping("/realtimeData")
public class RealtimeDataController {

	@Autowired
	private RealtimeDataService realtimeDataService;
	
	/***
	 * -查询实时数据
	 * @param collectionPoint
	 * @param collectionTime
	 * @return
	 */
	@GetMapping("/list")
	public R<List<RealtimeDataVo>> list(String collectionPoint, String collectionTime){
		R<List<RealtimeDataVo>> baseResp = new R<List<RealtimeDataVo>>();
		if(!StringUtils.isEmpty(collectionPoint) && !StringUtils.isEmpty(collectionTime)) {
			baseResp.setCode(R.SUCCESS);
			baseResp.setData(realtimeDataService.realtimeDataVo(collectionPoint, collectionTime));
		}else {
			baseResp.setCode(R.NULL_PARAMETER);
			baseResp.setMsg(R.NULL_PARAMETER_MSG);
		}
		return baseResp;
	}
	
}

