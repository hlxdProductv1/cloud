package com.hlxd.microcloud.controller;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.entity.Workshop;
import com.hlxd.microcloud.service.WorkshopService;

/***
 * -车间表  前端控制器
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
@RestController
@RequestMapping("/workshop")
public class WorkshopController {

	@Autowired
	private WorkshopService workshopService;
	
	/***
	 * -查询车间
	 * @return
	 */
	@GetMapping("/list")
	public R<List<Workshop>> list(){
		R<List<Workshop>> r = new R<>();
		r.setCode(R.SUCCESS);
		r.setData(workshopService.selectList(null));
		return r;
	}
	
	/***
	 * -添加车间
	 * @param workshopName
	 * @return
	 */
	@PostMapping("/save")
	public R<Boolean> save(String workshopName){
		R<Boolean> r = new R<>();
		if(!StringUtils.isEmpty(workshopName)) {
			r.setCode(R.SUCCESS);
			r.setData(workshopService.insert(new Workshop(workshopName)));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -修改车间
	 * @param workshop
	 * @return
	 */
	@PostMapping("/update")
	public R<Boolean> update(Workshop workshop){
		R<Boolean> r = new R<>();
		if(workshop!=null && workshop.getId()!=null && !StringUtils.isEmpty(workshop.getWorkshopName())) {
			r.setCode(R.SUCCESS);
			r.setData(workshopService.updateById(workshop));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -删除车间
	 * @param id
	 * @return
	 */
	@PostMapping("/remove")
	public R<Boolean> remove(Integer id){
		R<Boolean> r = new R<>();
		if(id!=null) {
			r.setCode(R.SUCCESS);
			r.setData(workshopService.deleteById(id));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
}

