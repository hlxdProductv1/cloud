package com.hlxd.microcloud.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.plugins.Page;
import com.hlxd.microcloud.entity.CollectionPoint;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.entity.vo.CollectionPointVo;
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
			CollectionPoint collection = collectionPointService.selectById(collectionPoint);
			if(collection==null){
				CollectionPoint entity = new CollectionPoint(collectionPoint,standardCode,equipmentCode);
				r.setCode(R.SUCCESS);
				r.setData(collectionPointService.insert(entity));
			}else {
				r.setCode(R.SUCCESS);
				r.setData(false);
			}
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
	 * @param page
	 * @param organizeCode 生产厂
	 * @param brandCode 品牌
	 * @param technologyCode 工艺
	 * @param equipmentName 设备
	 * @param standardName 生产标准
	 * @param collectionPoint 数采点
	 * @return
	 */
	@GetMapping("/list")
	public R<Page<CollectionPointVo>> list(Integer current, Integer size, String organizeCode, String brandCode,
			String technologyCode, String equipmentName, String standardName, String collectionPoint){
		R<Page<CollectionPointVo>> r = new R<>();
		if(!StringUtils.isEmpty(organizeCode) && current!=null && size!=null) {
			r.setCode(R.SUCCESS);
			r.setData(collectionPointService.selectcollectionPointVo(new Page<CollectionPointVo>(current, size), organizeCode,
					brandCode, technologyCode, equipmentName, standardName, collectionPoint));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
}

