package com.hlxd.microcloud.mapper;

import com.hlxd.microcloud.entity.CollectionPoint;
import com.hlxd.microcloud.entity.vo.CollectionPointVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/***
 * - 数采点  Mapper 接口
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
public interface CollectionPointMapper extends BaseMapper<CollectionPoint> {

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
	List<CollectionPointVo> selectcollectionPointVo(Pagination page,@Param("organizeCode") String organizeCode, 
			@Param("brandCode")String brandCode, @Param("technologyCode")String technologyCode, 
			@Param("equipmentName")String equipmentName, @Param("standardName")String standardName, 
			@Param("collectionPoint")String collectionPoint);
}
