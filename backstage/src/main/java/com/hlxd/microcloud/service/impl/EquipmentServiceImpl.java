package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.entity.Equipment;
import com.hlxd.microcloud.entity.vo.EquipmentTreeVo;
import com.hlxd.microcloud.mapper.EquipmentMapper;
import com.hlxd.microcloud.service.EquipmentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * -设备  服务实现类
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
@Service
public class EquipmentServiceImpl extends ServiceImpl<EquipmentMapper, Equipment> implements EquipmentService {

	@Autowired
	private EquipmentMapper equipmentMapper;

	@Override
	public List<EquipmentTreeVo> equipmentTree(String organizeCode, Integer equipmentType,
			String superiorEquipmentCode) {
		return equipmentMapper.equipmentTree(organizeCode, equipmentType, superiorEquipmentCode);
	}
	
	

}
