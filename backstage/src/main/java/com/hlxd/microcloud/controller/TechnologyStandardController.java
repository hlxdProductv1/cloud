package com.hlxd.microcloud.controller;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.entity.TechnologyStandard;
import com.hlxd.microcloud.entity.TechnologyStandardValue;
import com.hlxd.microcloud.entity.vo.TechnologyStandardVo;
import com.hlxd.microcloud.service.TechnologyStandardService;
import com.hlxd.microcloud.service.TechnologyStandardValueService;

/***
 * - 生产工艺标准  前端控制器
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
@RestController
@RequestMapping("/technologyStandard")
public class TechnologyStandardController {
	
	@Autowired
	private TechnologyStandardService technologyStandardService;
	@Autowired
	private TechnologyStandardValueService technologyStandardValueService;
	
	/***
	 * -查询系列生产工艺标准
	 * @param current
	 * @param size
	 * @param technologyCode
	 * @return
	 */
	@GetMapping("/list")
	public R<Page<TechnologyStandardVo>> list(Integer current, Integer size, String standardName, String technologyCode){
		R<Page<TechnologyStandardVo>> r = new R<>();
		if(current!=null && size!=null) {
			r.setCode(R.SUCCESS);
			r.setData(technologyStandardService.list(new Page<TechnologyStandardVo>(current, size), standardName, technologyCode));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -工艺标准下拉列表
	 * @param technologyCode
	 * @return
	 */
	@GetMapping("/select")
	public R<List<TechnologyStandard>> select(String technologyCode){
		R<List<TechnologyStandard>> r = new R<>();
		if(!StringUtils.isEmpty(technologyCode)) {
			Wrapper<TechnologyStandard> wrapper = new EntityWrapper<TechnologyStandard>();
			if(!StringUtils.isEmpty(technologyCode)){
				wrapper.and().eq("technology_code", technologyCode);
			}
			r.setCode(R.SUCCESS);
			r.setData(technologyStandardService.selectList(wrapper));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -添加生产工艺标准
	 * @param entity
	 * @return
	 */
	@PostMapping("/save")
	public R<Boolean> save(TechnologyStandard entity){
		R<Boolean> r = new R<>();
		if(entity!=null) {
			StringBuilder code = new StringBuilder();
			code.append(entity.getTechnologyCode());
			code.append(((int)(Math.random()*900 + 100))).toString();
			entity.setStandardCode(code.toString());
			r.setCode(R.SUCCESS);
			r.setData(technologyStandardService.insert(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -删除生产工艺标准
	 * @param standardCode
	 * @return
	 */
	@PostMapping("/remove")
	public R<Boolean> remove(String standardCode){
		R<Boolean> r = new R<>();
		if(!StringUtils.isEmpty(standardCode)) {
			boolean bl = technologyStandardValueService.delete(new EntityWrapper<TechnologyStandardValue>().eq("standard_code", standardCode));
			if(bl == true) {
				bl = technologyStandardService.deleteById(standardCode);
			}
			r.setCode(R.SUCCESS);
			r.setData(bl);
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -修改生产工艺标准
	 * @param entity
	 * @return
	 */
	@PostMapping("/update")
	public R<Boolean> update(TechnologyStandard entity){
		R<Boolean> r = new R<>();
		if(entity!=null) {
			r.setCode(R.SUCCESS);
			r.setData(technologyStandardService.updateById(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}

}
