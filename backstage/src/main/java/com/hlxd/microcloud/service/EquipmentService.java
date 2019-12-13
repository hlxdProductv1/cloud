package com.hlxd.microcloud.service;

import com.hlxd.microcloud.entity.Equipment;
import com.hlxd.microcloud.entity.vo.EquipmentTreeVo;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/***
 * -设备  服务类
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
public interface EquipmentService extends IService<Equipment> {
	/***
	 * -设备结构树
	 * @param organizeCode
	 * @return
	 */
	List<EquipmentTreeVo> equipmentTree(String organizeCode, Integer equipmentType,String superiorEquipmentCode);
}
