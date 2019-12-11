package com.hlxd.microcloud.controller;

import java.util.Calendar;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hlxd.microcloud.entity.Equipment;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.entity.vo.EquipmentTreeVo;
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
	@CacheEvict(value="equipment",allEntries=true)
	public R<Boolean> save(String equipmentName, String superiorEquipmentCode, String organizeCode){
		R<Boolean> r = new R<Boolean>();
		if(!StringUtils.isEmpty(equipmentName) && !StringUtils.isEmpty(organizeCode) && !StringUtils.isEmpty(superiorEquipmentCode)) {
			Equipment equipment = equipmentService.selectById(superiorEquipmentCode);
			if(equipment == null || equipment.getEquipmentType()==null) {
				equipment = new  Equipment();
				 equipment.setEquipmentType(0);
			}
			Equipment entity = new Equipment(equipmentName, superiorEquipmentCode, (equipment.getEquipmentType()+1), organizeCode);
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
	@CacheEvict(value="equipment",allEntries=true)
	public R<Boolean> update(String equipmentCode, String equipmentName){
		R<Boolean> r = new R<Boolean>();
		if(!StringUtils.isEmpty(equipmentName) && !StringUtils.isEmpty(equipmentCode)) {
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
	@CacheEvict(value="equipment",allEntries=true)
	public R<Boolean> remove(String equipmentCode){
		R<Boolean> r = new R<Boolean>();
		if(!StringUtils.isEmpty(equipmentCode)) {
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
	@GetMapping("/select")
	public R<List<Equipment>> select(String organizeCode,String equipmentName){
		R<List<Equipment>> r = new R<>();
		if(!StringUtils.isEmpty(equipmentName) && !StringUtils.isEmpty(organizeCode)) {
			r.setCode(R.SUCCESS);
			r.setData(equipmentService.selectList(new EntityWrapper<Equipment>().eq("organize_code", organizeCode)
					.and().like("equipment_name", equipmentName)));
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
	public R<Page<Equipment>> list(Integer current, Integer size, String organizeCode, String superiorEquipmentCode){
		R<Page<Equipment>> r = new R<>();
		if(current!=null && size!=null) {
			r.setCode(R.SUCCESS);
			if(!StringUtils.isEmpty(superiorEquipmentCode) && !StringUtils.isEmpty(organizeCode)) {
				r.setData(equipmentService.selectPage(new Page<Equipment>(current, size),
						new EntityWrapper<Equipment>().eq("organize_code", organizeCode).eq("superior_equipment_code", superiorEquipmentCode)));
			}
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -设备树
	 * @param organizeCode
	 * @param equipmentType
	 * @param superiorEquipmentCode
	 * @return
	 */
	@GetMapping("/tree")
	@Cacheable(value="equipment")
	public R<List<EquipmentTreeVo>> tree(String organizeCode){
		R<List<EquipmentTreeVo>> r = new R<>();
		List<EquipmentTreeVo> tree = equipmentService.equipmentTree(null, 0, null);
		if(tree!=null && tree.size()>0) {
			tree.get(0).setSpread(true);
			digui(tree,organizeCode);
		}
		r.setCode(R.SUCCESS);
		r.setData(tree);
	    return r;
	}
	
	/***
	 * -循环树形结构
	 * @param list
	 */
	private void digui(List<EquipmentTreeVo> list,String organizeCode) {
		List<EquipmentTreeVo> tree = null;
		EquipmentTreeVo o = null;
        for (int i=0,length=list.size();i<length;i++) {
        	o = list.get(i);
        	tree = equipmentService.equipmentTree(organizeCode, null, o.getId());
            if (tree!=null && tree.size() > 0) {
            	o.setChildren(tree);
                digui(tree,organizeCode);
            }else {
            	break;
            }
        }
	}
}

