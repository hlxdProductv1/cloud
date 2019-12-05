package com.hlxd.microcloud.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hlxd.microcloud.entity.CollectionPoint;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.service.CollectionPointService;

/***
 * -数采点  前端控制器
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
@RestController
@RequestMapping("/collectionPoint")
public class CollectionPointController {

	@Autowired
	private CollectionPointService collectionPointService;
	
	/***
	 * -添加数采点
	 * @param entity
	 * @return
	 */
	@PostMapping("/save")
	public R<Boolean> save(String collectionPoint, String standardCode, String equipmentCode){
		R<Boolean> r = new R<Boolean>();
		if(!StringUtils.isEmpty(collectionPoint) && !StringUtils.isEmpty(standardCode)&& !StringUtils.isEmpty(equipmentCode)) {
			CollectionPoint entity = new CollectionPoint(collectionPoint,standardCode,equipmentCode);
			r.setCode(R.SUCCESS);
			r.setData(collectionPointService.insert(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -删除数采点
	 * @param collectionPoint
	 * @return
	 */
	@PostMapping("/remove")
	public R<Boolean> remove(String collectionPoint){
		R<Boolean> r = new R<Boolean>();
		if(!StringUtils.isEmpty(collectionPoint)) {
			r.setCode(R.SUCCESS);
			r.setData(collectionPointService.deleteById(collectionPoint));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -查询数采点
	 * @param equipmentCode
	 * @return
	 */
	@GetMapping("/list")
	public R<List<CollectionPoint>> list(String equipmentCode){
		R<List<CollectionPoint>> r = new R<List<CollectionPoint>>();
		if(!StringUtils.isEmpty(equipmentCode)) {
			r.setCode(R.SUCCESS);
			r.setData(collectionPointService.selectList(new EntityWrapper<CollectionPoint>().eq("equipment_code", equipmentCode)));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
}

