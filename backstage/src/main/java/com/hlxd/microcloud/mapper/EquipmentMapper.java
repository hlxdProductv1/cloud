package com.hlxd.microcloud.mapper;

import com.hlxd.microcloud.entity.Equipment;
import com.hlxd.microcloud.entity.vo.EquipmentTreeVo;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/***
 * -设备  Mapper 接口
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
public interface EquipmentMapper extends BaseMapper<Equipment> {

	/***
	 * -设备结构树
	 * @param organizeCode
	 * @return
	 */
	List<EquipmentTreeVo> equipmentTree(@Param("organizeCode")String organizeCode, @Param("equipmentType")Integer equipmentType,
			@Param("superiorEquipmentCode")String superiorEquipmentCode);
}
