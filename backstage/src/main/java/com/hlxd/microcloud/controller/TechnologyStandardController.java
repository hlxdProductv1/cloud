package com.hlxd.microcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.plugins.Page;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.entity.TechnologyStandard;
import com.hlxd.microcloud.entity.vo.TechnologyStandardVo;
import com.hlxd.microcloud.service.TechnologyStandardService;

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

	/***
	 * -查询单个生产工艺标准
	 * @param standardCode
	 * @return
	 */
	@GetMapping("/get")
	public R<TechnologyStandard> get(String standardCode){
		R<TechnologyStandard> r = new R<>();
		if(standardCode!=null && !"".equals(standardCode)) {
			r.setCode(R.SUCCESS);
			TechnologyStandard entity = technologyStandardService.selectById(standardCode);
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
	 * -查询系列生产工艺标准
	 * @param current
	 * @param size
	 * @param technologyCode
	 * @return
	 */
	@GetMapping("/list")
	public R<Page<TechnologyStandardVo>> list(Integer current, Integer size, String technologyCode){
		R<Page<TechnologyStandardVo>> r = new R<>();
		if(current!=null && size!=null) {
			r.setCode(R.SUCCESS);
			r.setData(technologyStandardService.list(new Page<TechnologyStandardVo>(current, size), technologyCode));
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
			StringBuilder code = new StringBuilder(entity.getCigaretteCode());
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
		if(standardCode!=null && !"".equals(standardCode)) {
			r.setCode(R.SUCCESS);
			r.setData(technologyStandardService.deleteById(standardCode));
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

