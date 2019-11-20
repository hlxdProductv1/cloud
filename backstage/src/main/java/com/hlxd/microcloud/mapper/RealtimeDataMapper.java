package com.hlxd.microcloud.mapper;

import com.hlxd.microcloud.entity.RealtimeData;
import com.hlxd.microcloud.entity.vo.RealtimeDataVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/***
 * -实时数据  Mapper 接口
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
public interface RealtimeDataMapper extends BaseMapper<RealtimeData> {
	
	/***
	 * -查询采集点实时数据
	 * @param collectionPoint
	 * @param collectionTime
	 * @return
	 */
	List<RealtimeDataVo> realtimeDataVo(@Param("collectionPoint")String collectionPoint, @Param("collectionTime")String collectionTime);
}
