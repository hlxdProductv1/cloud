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

/**
 * <p>
 * 生产工艺标准  前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-11-14
 */
@RestController
@RequestMapping("/technologyStandard")
public class TechnologyStandardController {
	
	@Autowired
	private TechnologyStandardService technologyStandardService;

	@GetMapping("/get")
	public R<TechnologyStandard> get(String standardCode){
		R<TechnologyStandard> r = new R<>();
		if(standardCode!=null && !"".equals(standardCode)) {
			r.setCode(R.SUCCESS);
			r.setData(technologyStandardService.selectById(standardCode));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg("The parameter is empty.");
		}
		return r;
	}
	
	@GetMapping("/list")
	public R<Page<TechnologyStandardVo>> list(Integer current, Integer size, String technologyCode){
		R<Page<TechnologyStandardVo>> r = new R<>();
		if(current!=null && size!=null) {
			r.setCode(R.SUCCESS);
			r.setData(technologyStandardService.list(new Page<TechnologyStandardVo>(current, size), technologyCode));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg("The parameter is empty.");
		}
		return r;
	}
	
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
			r.setMsg("The parameter is empty.");
		}
		return r;
	}
	
	@PostMapping("/remove")
	public R<Boolean> remove(String standardCode){
		R<Boolean> r = new R<>();
		if(standardCode!=null && !"".equals(standardCode)) {
			r.setCode(R.SUCCESS);
			r.setData(technologyStandardService.deleteById(standardCode));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg("The parameter is empty.");
		}
		return r;
	}
	
	@PostMapping("/update")
	public R<Boolean> update(TechnologyStandard entity){
		R<Boolean> r = new R<>();
		if(entity!=null) {
			r.setCode(R.SUCCESS);
			r.setData(technologyStandardService.updateById(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg("The parameter is empty.");
		}
		return r;
	}
}

