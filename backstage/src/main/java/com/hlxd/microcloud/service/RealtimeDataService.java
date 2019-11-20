package com.hlxd.microcloud.service;

import com.hlxd.microcloud.entity.RealtimeData;
import com.hlxd.microcloud.entity.vo.RealtimeDataVo;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/***
 * -实时数据  服务类
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
public interface RealtimeDataService extends IService<RealtimeData> {
	/***
	 * -查询采集点实时数据
	 * @param collectionPoint
	 * @param collectionTime
	 * @return
	 */
	List<RealtimeDataVo> realtimeDataVo(String collectionPoint, String collectionTime);
}
