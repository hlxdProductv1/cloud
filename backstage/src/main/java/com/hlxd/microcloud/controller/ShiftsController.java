package com.hlxd.microcloud.controller;

import java.util.Calendar;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hlxd.microcloud.entity.Shifts;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.service.ShiftsService;

/***
 * -班次表  前端控制器
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月21日
 */
@RestController
@RequestMapping("/shifts")
public class ShiftsController {
	@Autowired
	private ShiftsService shiftsService;
	
	/***
	 * -添加班次
	 * @param entity
	 * @return
	 */
	@PostMapping("/save")
	public R<Boolean> save(String organizeCode, String shiftsName){
		R<Boolean> r = new R<Boolean>();
		if(!StringUtils.isEmpty(organizeCode) && !StringUtils.isEmpty(shiftsName)) {
			Shifts entity = new Shifts();
			entity.setOrganizeCode(organizeCode);
			StringBuilder code = new StringBuilder(entity.getOrganizeCode());
			code.append((Calendar.getInstance().getTimeInMillis()+"").substring(9));
			code.append(((int)(Math.random()*900 + 100))).toString();
			entity.setShiftsCode(code.toString());
			entity.setShiftsName(shiftsName);
			r.setCode(R.SUCCESS);
			r.setData(shiftsService.insert(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -删除班次
	 * @param entity
	 * @return
	 */
	@PostMapping("/remove")
	public R<Boolean> remove(String shiftsCode){
		R<Boolean> r = new R<Boolean>();
		if(!StringUtils.isEmpty(shiftsCode)) {
			r.setCode(R.SUCCESS);
			r.setData(shiftsService.deleteById(shiftsCode));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -修改班次
	 * @param entity
	 * @return
	 */
	@PostMapping("/update")
	public R<Boolean> update(String shiftsCode, String shiftsName){
		R<Boolean> r = new R<Boolean>();
		if(!StringUtils.isEmpty(shiftsCode) && !StringUtils.isEmpty(shiftsName)) {
			Shifts entity = new Shifts();
			entity.setShiftsCode(shiftsCode);
			entity.setShiftsName(shiftsName);
			r.setCode(R.SUCCESS);
			r.setData(shiftsService.updateById(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -查询班次
	 * @param entity
	 * @return
	 */
	@GetMapping("/list")
	public R<List<Shifts>> list(String organizeCode){
		R<List<Shifts>> r = new R<List<Shifts>>();
		if(!StringUtils.isEmpty(organizeCode)) {
			r.setCode(R.SUCCESS);
			r.setData(shiftsService.selectList(new EntityWrapper<Shifts>().eq("organize_code", organizeCode)));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
}

