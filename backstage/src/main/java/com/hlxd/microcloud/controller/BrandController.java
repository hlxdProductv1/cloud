package com.hlxd.microcloud.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hlxd.microcloud.entity.Brand;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.service.BrandService;

/***
 * -品牌表  前端控制器
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月21日
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

	@Autowired
	private BrandService brandService;
	
	/***
	 * -添加品牌
	 * @param entity
	 * @return
	 */
	@PostMapping("/save")
	public R<Boolean> save(String brandCode, String brandName, String organizeCode){
		R<Boolean> r = new R<Boolean>();
		if(!StringUtils.isEmpty(brandCode) && !StringUtils.isEmpty(brandName) && !StringUtils.isEmpty(organizeCode)) {
			Brand entity = new Brand(brandCode,brandName,organizeCode);
			r.setCode(R.SUCCESS);
			r.setData(brandService.insert(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -删除品牌
	 * @param entity
	 * @return
	 */
	@PostMapping("/remove")
	public R<Boolean> remove(String brandCode){
		R<Boolean> r = new R<Boolean>();
		if(!StringUtils.isEmpty(brandCode)) {
			r.setCode(R.SUCCESS);
			r.setData(brandService.deleteById(brandCode));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -修改品牌
	 * @param entity
	 * @return
	 */
	@PostMapping("/update")
	public R<Boolean> update(String brandCode, String brandName){
		R<Boolean> r = new R<Boolean>();
		if(!StringUtils.isEmpty(brandCode) && !StringUtils.isEmpty(brandName)) {
			Brand entity = new Brand();
			entity.setBrandCode(brandCode);
			entity.setBrandName(brandName);
			r.setCode(R.SUCCESS);
			r.setData(brandService.updateById(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -查询单个品牌
	 * @param entity
	 * @return
	 */
	@GetMapping("/get")
	public R<Brand> get(String brandCode){
		R<Brand> r = new R<Brand>();
		if(!StringUtils.isEmpty(brandCode)) {
			r.setCode(R.SUCCESS);
			r.setData(brandService.selectById(brandCode));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -查询工厂下的所有品牌
	 * @param entity
	 * @return
	 */
	@GetMapping("/list")
	public R<Page<Brand>> list(Integer current, Integer size, String organizeCode){
		R<Page<Brand>> r = new R<Page<Brand>>();
		if(current!=null && size!=null && !StringUtils.isEmpty(organizeCode)) {
			r.setCode(R.SUCCESS);
			r.setData(brandService.selectPage(new Page<Brand>(current, size),new EntityWrapper<Brand>().eq("organize_code", organizeCode)));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}

}

