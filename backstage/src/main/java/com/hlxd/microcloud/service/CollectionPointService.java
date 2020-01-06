package com.hlxd.microcloud.service;

import com.hlxd.microcloud.entity.CollectionPoint;
import com.hlxd.microcloud.entity.vo.CollectionPointVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/***
 * -数采点  服务类
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
public interface CollectionPointService extends IService<CollectionPoint> {
	/***
	 * -查询数采绑定设备
	 * @param page
	 * @param organizeCode 生产厂
	 * @param brandCode 品牌
	 * @param technologyCode 工艺
	 * @param equipmentName 设备
	 * @param standardName 生产标准
	 * @param collectionPoint 数采点
	 * @return
	 */
	Page<CollectionPointVo> selectcollectionPointVo(Page<CollectionPointVo> page, String organizeCode,
			String technologyCode, String equipmentName, String standardName, String collectionPoint);
}
