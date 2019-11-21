package com.hlxd.microcloud.controller;

import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.entity.WorkshopSection;
import com.hlxd.microcloud.service.WorkshopSectionService;

/***
 * -生产工段  前端控制器
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
@RestController
@RequestMapping("/workshopSection")
public class WorkshopSectionController {

	@Autowired
	private WorkshopSectionService workshopSection;
	
	/***
	 * -添加生产工段
	 * @param workshopSectionName
	 * @param organizeCode
	 * @param technologyWorkshop
	 * @return
	 */
	@PostMapping("/save")
	public R<Boolean> save(String workshopSectionName, String organizeCode, Integer technologyWorkshop){
		R<Boolean> r = new R<Boolean>();
		if(workshopSectionName!=null && !"".equals(workshopSectionName)
				&& organizeCode!=null && !"".equals(organizeCode)) {
			WorkshopSection entity = new WorkshopSection();
			StringBuilder code = new StringBuilder(organizeCode);
			code.append((Calendar.getInstance().getTimeInMillis()+"").substring(9));
			code.append(((int)(Math.random()*900 + 100))).toString();
			entity.setWorkshopSectionCode(code.toString());
			entity.setWorkshopSectionName(workshopSectionName);
			entity.setOrganizeCode(organizeCode);
			r.setCode(R.SUCCESS);
			r.setData(workshopSection.insert(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -删除生产工段
	 * @param workshopSectionCode
	 * @return
	 */
	@PostMapping("/remove")
	public R<Boolean> remove(String workshopSectionCode){
		R<Boolean> r = new R<Boolean>();
		if(workshopSectionCode!=null && !"".equals(workshopSectionCode)) {
			r.setCode(R.SUCCESS);
			r.setData(workshopSection.deleteById(workshopSectionCode));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -修改生产工段
	 * @param entity
	 * @return
	 */
	@PostMapping("/update")
	public R<Boolean> update(WorkshopSection entity){
		R<Boolean> r = new R<Boolean>();
		if(entity!=null && entity.getWorkshopSectionName()!=null && !"".equals(entity.getWorkshopSectionName())
				&& entity.getWorkshopSectionCode()!=null && !"".equals(entity.getWorkshopSectionCode())) {
			r.setCode(R.SUCCESS);
			r.setData(workshopSection.updateById(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -查询生产工段
	 * @param organizeCode
	 * @param technologyWorkshop
	 * @return
	 */
	@GetMapping("/list")
	public R<List<WorkshopSection>> list(String organizeCode, Integer technologyWorkshop){
		R<List<WorkshopSection>> r = new R<List<WorkshopSection>>();
		if(technologyWorkshop!=null) {
		r.setCode(R.SUCCESS);
		r.setData(workshopSection.selectList(new EntityWrapper<WorkshopSection>().eq("organize_code", organizeCode)
				.and().eq("technology_workshop", technologyWorkshop).or().isNull("organize_code")));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
}

