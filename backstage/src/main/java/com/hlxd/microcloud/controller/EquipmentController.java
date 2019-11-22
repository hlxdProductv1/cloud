package com.hlxd.microcloud.controller;

import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hlxd.microcloud.entity.Equipment;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.service.EquipmentService;

/***
 * -设备控制层
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
@RestController
@RequestMapping("/equipment")
public class EquipmentController {

	@Autowired
	private EquipmentService equipmentService;
	
	/***
	 * -添加设备
	 * @param entity
	 * @return
	 */
	@PostMapping("/save")
	public R<Boolean> save(String equipmentName, String superiorEquipmentCode, Integer equipmentType, String organizeCode){
		R<Boolean> r = new R<Boolean>();
		if(equipmentName!=null && equipmentType!=null && organizeCode!=null
				&& !"".equals(equipmentName) && !"".equals(organizeCode)) {
			Equipment entity = new Equipment(equipmentName, superiorEquipmentCode, equipmentType, organizeCode);
			StringBuilder code = new StringBuilder(entity.getOrganizeCode());
			code.append((Calendar.getInstance().getTimeInMillis()+"").substring(9));
			code.append(((int)(Math.random()*900 + 100))).toString();
			entity.setEquipmentCode(code.toString());
			r.setCode(R.SUCCESS);
			r.setData(equipmentService.insert(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -修改设备
	 * @param entity
	 * @return
	 */
	@PostMapping("/update")
	public R<Boolean> update(String equipmentCode, String equipmentName){
		R<Boolean> r = new R<Boolean>();
		if(equipmentName!=null && !"".equals(equipmentName) && equipmentCode!=null && !"".equals(equipmentCode)) {
			Equipment entity = new Equipment(equipmentCode, equipmentName);
			r.setCode(R.SUCCESS);
			r.setData(equipmentService.updateById(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -删除设备
	 * @param equipmentCode
	 * @return
	 */
	@PostMapping("/remove")
	public R<Boolean> remove(String equipmentCode){
		R<Boolean> r = new R<Boolean>();
		if(equipmentCode!=null && !"".equals(equipmentCode)) {
			int count = equipmentService.selectCount(new EntityWrapper<Equipment>().eq("superior_equipment_code", equipmentCode));
			if(count==0) {
				r.setCode(R.SUCCESS);
				r.setData(equipmentService.deleteById(equipmentCode));
			}else {
				r.setCode(R.NO_PERMISSION);
				r.setData(false);
				r.setMsg(R.NO_PERMISSION_MSG);
			}
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -查询单个设备
	 * @param equipmentCode
	 * @exception UserNotFoundException
	 * @return
	 */
	@GetMapping("/get")
	public R<Equipment> get(String equipmentCode){
		R<Equipment> r = new R<Equipment>();
		if(equipmentCode!=null && !"".equals(equipmentCode)) {
			r.setCode(R.SUCCESS);
			Equipment entity = equipmentService.selectById(equipmentCode);
			r.setData(entity);
			if(entity==null) {
				r.setMsg(R.NULL_QUERY);
			}
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -查询设备列表
	 * @param current
	 * @param size
	 * @param organizeCode
	 * @param equipmentType
	 * @param superiorEquipmentCode
	 * @return
	 */
	@GetMapping("/list")
	public R<Page<Equipment>> list(Integer current, Integer size, String organizeCode, Integer equipmentType, String superiorEquipmentCode){
		R<Page<Equipment>> r = new R<>();
		if(current!=null && size!=null) {
			r.setCode(R.SUCCESS);
			if(equipmentType!=null && organizeCode!=null && !"".equals(organizeCode)) {
				r.setData(equipmentService.selectPage(new Page<Equipment>(current, size),
						new EntityWrapper<Equipment>().eq("organize_code", organizeCode).eq("equipment_type", equipmentType)));
			}else if(superiorEquipmentCode!=null && !"".equals(superiorEquipmentCode)){
				r.setData(equipmentService.selectPage(new Page<Equipment>(current, size),
						new EntityWrapper<Equipment>().eq("superior_equipment_code", superiorEquipmentCode)));
			}
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
}

