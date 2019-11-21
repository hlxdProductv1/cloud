package com.hlxd.microcloud.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hlxd.microcloud.entity.ProductionLineTechnology;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.entity.vo.ProductionLineVo;
import com.hlxd.microcloud.service.ProductionLineTechnologyService;

/***
 * -工艺生产线工艺  前端控制器
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
@RestController
@RequestMapping("/productionLineTechnology")
public class ProductionLineTechnologyController {

	@Autowired
	private ProductionLineTechnologyService productionLineTechnologyService;
	
	/***
	 * -添加工艺生产线工艺
	 * @param entity
	 * @return
	 */
	@PostMapping("/save")
	public R<Boolean> save(ProductionLineTechnology entity){
		R<Boolean> r = new R<Boolean>();
		if(entity!=null && entity.getProductionLineCode()!=null && entity.getSerialNumber()!=null && entity.getTechnologyCode()!=null
				&& !"".equals(entity.getProductionLineCode()) && !"".equals(entity.getTechnologyCode())) {
			entity.setId(UUID.randomUUID().toString());
			r.setCode(R.SUCCESS);
			r.setData(productionLineTechnologyService.insert(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -修改工艺生产线工艺
	 * @param id
	 * @param serialNumber
	 * @return
	 */
	@PostMapping("/update")
	public R<Boolean> update(String id, Integer serialNumber){
		R<Boolean> r = new R<Boolean>();
		if(id!=null && !"".equals(id) && serialNumber!=null) {
			ProductionLineTechnology entity = new ProductionLineTechnology();
			entity.setId(id);
			entity.setSerialNumber(serialNumber);
			r.setCode(R.SUCCESS);
			r.setData(productionLineTechnologyService.updateById(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -删除工艺生产线工艺
	 * @param id
	 * @return
	 */
	@PostMapping("/remove")
	public R<Boolean> remove(String id){
		R<Boolean> r = new R<Boolean>();
		if(id!=null && !"".equals(id)) {
			r.setCode(R.SUCCESS);
			r.setData(productionLineTechnologyService.deleteById(id));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -查询工艺生产线工艺
	 * @param productionLineCode
	 * @return
	 */
	@GetMapping("/list")
	public R<List<ProductionLineVo>> list(String productionLineCode){
		R<List<ProductionLineVo>> r = new R<List<ProductionLineVo>>();
		if(productionLineCode!=null && !"".equals(productionLineCode)) {
			r.setCode(R.SUCCESS);
			r.setData(productionLineTechnologyService.productionLine(productionLineCode));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
}

