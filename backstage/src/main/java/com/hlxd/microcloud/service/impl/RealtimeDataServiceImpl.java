package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.entity.RealtimeData;
import com.hlxd.microcloud.entity.vo.RealtimeDataVo;
import com.hlxd.microcloud.mapper.RealtimeDataMapper;
import com.hlxd.microcloud.service.RealtimeDataService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * -实时数据  服务实现类
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
@Service
public class RealtimeDataServiceImpl extends ServiceImpl<RealtimeDataMapper, RealtimeData> implements RealtimeDataService {

	@Autowired
	private RealtimeDataMapper realtimeDataMapper;
	
	@Override
	public List<RealtimeDataVo> realtimeDataVo(String collectionPoint, String collectionTime) {
		return realtimeDataMapper.realtimeDataVo(collectionPoint, collectionTime);
	}

}
