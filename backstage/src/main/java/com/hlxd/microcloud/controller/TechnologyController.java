package com.hlxd.microcloud.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.entity.Technology;
import com.hlxd.microcloud.service.TechnologyService;

/**
 * <p>
 * 生产工艺  前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-11-13
 */
@RestController
@RequestMapping("/technology")
public class TechnologyController {

	@Autowired
	private TechnologyService technologyService;
	
	@PostMapping("/save")
	public R<Boolean> save(Technology technology){
		R<Boolean> r = new R<>();
		if(technology!=null && technology.getWorkshopSectionCode()!=null && technology.getTechnologyWorkshop()!=null) {
			StringBuilder code = new StringBuilder(technology.getOrganizeCode());
			code.append(Calendar.getInstance().getTimeInMillis()+"");
			code.append(((int)(Math.random()*900 + 100))).toString();
			technology.setTechnologyCode(code.toString());
			technology.setOrganizeCode("".equals(technology.getOrganizeCode())?null:technology.getOrganizeCode());
			r.setCode(R.SUCCESS);
			r.setData(technologyService.insert(technology));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg("The parameter is empty.");
		}
		return r;
	}
	
	@PostMapping("/remove")
	public R<Boolean> remove(String technologyCode){
		R<Boolean> r = new R<>();
		if(technologyCode!=null && !"".equals(technologyCode)) {
			r.setCode(R.SUCCESS);
			r.setData(technologyService.deleteById(technologyCode));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg("The parameter is empty.");
		}
		return r;
	}
	
	@PostMapping("/update")
	public R<Boolean> update(String technologyCode, String technologyName){
		R<Boolean> r = new R<>();
		if(technologyCode!=null && technologyName!=null && !"".equals(technologyCode) && !"".equals(technologyName)) {
			Technology entity = new Technology();
			entity.setTechnologyCode(technologyCode);
			entity.setTechnologyName(technologyName);
			r.setCode(R.SUCCESS);
			r.setData(technologyService.updateById(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg("The parameter is empty.");
		}
		return r;
	}
	
	@GetMapping("/list")
	public R<Page<Technology>> list(Integer current, Integer size, Integer technologyWorkshop, String organizeCode){
		R<Page<Technology>> r = new R<Page<Technology>>();
		if(current!=null && size!=null && technologyWorkshop!=null && organizeCode!=null && !"".equals(organizeCode)) {
			r.setCode(200);
			r.setData(technologyService.selectPage(new Page<Technology>(current, size), new EntityWrapper<Technology>().eq("technology_workshop", technologyWorkshop)
					.and().eq("organize_code", organizeCode).or().isNull("organize_code")));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg("The parameter is empty.");
		}
		return r;
	}
}

