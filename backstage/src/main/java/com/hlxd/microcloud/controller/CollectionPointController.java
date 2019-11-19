package com.hlxd.microcloud.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hlxd.microcloud.entity.CollectionPoint;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.service.CollectionPointService;

/**
 * <p>
 * 数采点  前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-11-14
 */
@RestController
@RequestMapping("/collectionPoint")
public class CollectionPointController {

	@Autowired
	private CollectionPointService collectionPointService;
	
	@PostMapping("/save")
	public R<Boolean> save(CollectionPoint entity){
		R<Boolean> r = new R<Boolean>();
		if(entity!=null) {
			r.setCode(R.SUCCESS);
			r.setData(collectionPointService.insert(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg("The parameter is empty.");
		}
		return r;
	}
	
	@PostMapping("/remove")
	public R<Boolean> remove(String collectionPoint){
		R<Boolean> r = new R<Boolean>();
		if(collectionPoint!=null && !"".equals(collectionPoint)) {
			r.setCode(R.SUCCESS);
			r.setData(collectionPointService.deleteById(collectionPoint));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg("The parameter is empty.");
		}
		return r;
	}
	
	@GetMapping("/list")
	public R<List<CollectionPoint>> list(String equipmentCode){
		R<List<CollectionPoint>> r = new R<List<CollectionPoint>>();
		if(equipmentCode!=null && !"".equals(equipmentCode)) {
			r.setCode(R.SUCCESS);
			r.setData(collectionPointService.selectList(new EntityWrapper<CollectionPoint>().eq("equipment_code", equipmentCode)));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg("The parameter is empty.");
		}
		return r;
	}
	
}

