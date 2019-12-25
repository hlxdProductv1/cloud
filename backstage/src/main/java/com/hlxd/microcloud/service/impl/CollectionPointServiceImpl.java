package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.entity.CollectionPoint;
import com.hlxd.microcloud.entity.vo.CollectionPointVo;
import com.hlxd.microcloud.mapper.CollectionPointMapper;
import com.hlxd.microcloud.service.CollectionPointService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * -数采点  服务实现类
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
@Service
public class CollectionPointServiceImpl extends ServiceImpl<CollectionPointMapper, CollectionPoint> implements CollectionPointService {

	@Autowired
	private CollectionPointMapper collectionPointMapper;
	
	@Override
	public Page<CollectionPointVo> selectcollectionPointVo(Page<CollectionPointVo> page, String organizeCode,
			String technologyCode, String equipmentName, String standardName,
			String collectionPoint) {
		// TODO Auto-generated method stub
		return page.setRecords(collectionPointMapper.selectcollectionPointVo(page, organizeCode, technologyCode, equipmentName, standardName, collectionPoint));
	}

}
