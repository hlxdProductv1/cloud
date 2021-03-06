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
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.entity.Technology;
import com.hlxd.microcloud.service.TechnologyService;

/***
 * -生产工艺  前端控制器
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
@RestController
@RequestMapping("/technology")
public class TechnologyController {

	@Autowired
	private TechnologyService technologyService;
	
	/***
	 * -添加生产工艺  
	 * @param technology
	 * @return
	 */
	@PostMapping("/save")
	public R<Boolean> save(Technology technology){
		R<Boolean> r = new R<>();
		if(technology!=null && !StringUtils.isEmpty(technology.getWorkshopSectionCode()) && technology.getTechnologyWorkshop()!=null) {
			StringBuilder code = new StringBuilder(technology.getOrganizeCode());
			code.append((Calendar.getInstance().getTimeInMillis()+"").substring(9));
			code.append(((int)(Math.random()*900 + 100))).toString();
			technology.setTechnologyCode(code.toString());
			technology.setOrganizeCode("".equals(technology.getOrganizeCode())?null:technology.getOrganizeCode());
			r.setCode(R.SUCCESS);
			r.setData(technologyService.insert(technology));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -删除生产工艺  
	 * @param technologyCode
	 * @return
	 */
	@PostMapping("/remove")
	public R<Boolean> remove(String technologyCode){
		R<Boolean> r = new R<>();
		if(!StringUtils.isEmpty(technologyCode)) {
			r.setCode(R.SUCCESS);
			r.setData(technologyService.deleteById(technologyCode));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -修改生产工艺  
	 * @param technologyCode
	 * @param technologyName
	 * @return
	 */
	@PostMapping("/update")
	public R<Boolean> update(String technologyCode, String technologyName){
		R<Boolean> r = new R<>();
		if(!StringUtils.isEmpty(technologyCode) && !StringUtils.isEmpty(technologyName)) {
			Technology entity = new Technology();
			entity.setTechnologyCode(technologyCode);
			entity.setTechnologyName(technologyName);
			r.setCode(R.SUCCESS);
			r.setData(technologyService.updateById(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -查询生产工艺  
	 * @param current
	 * @param size
	 * @param technologyWorkshop
	 * @param organizeCode
	 * @return
	 */
	@GetMapping("/list")
	public R<List<Technology>> list(Integer technologyWorkshop, String organizeCode, String technologyName){
		R<List<Technology>> r = new R<List<Technology>>();
		if(technologyWorkshop!=null && !StringUtils.isEmpty(organizeCode)) {
			Wrapper<Technology> wrapper = new EntityWrapper<Technology>();
			wrapper.eq("technology_workshop", technologyWorkshop);
			if(!StringUtils.isEmpty(technologyName)) {
				wrapper.and().like("technology_name", technologyName);
			}
			wrapper.and().eq("organize_code", organizeCode).or().isNull("organize_code");
			r.setCode(200);
			r.setData(technologyService.selectList(wrapper));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -查询生产工艺  
	 * @param current
	 * @param size
	 * @param technologyWorkshop
	 * @param organizeCode
	 * @return
	 */
	@GetMapping("/select")
	public R<List<Technology>> select(String organizeCode){
		R<List<Technology>> r = new R<List<Technology>>();
		if(!StringUtils.isEmpty(organizeCode)) {
			Wrapper<Technology> wrapper = new EntityWrapper<Technology>();
			wrapper.and().eq("organize_code", organizeCode).or().isNull("organize_code");
			r.setCode(R.SUCCESS);
			r.setData(technologyService.selectList(wrapper));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
}

