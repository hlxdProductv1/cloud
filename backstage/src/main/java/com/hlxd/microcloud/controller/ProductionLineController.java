package com.hlxd.microcloud.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hlxd.microcloud.entity.ProductionLine;
import com.hlxd.microcloud.entity.ProductionLineTechnology;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.service.ProductionLineService;
import com.hlxd.microcloud.service.ProductionLineTechnologyService;

/**
 * <p>
 * 工艺生产线  前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-11-12
 */
@RestController
@RequestMapping("/productionLine")
public class ProductionLineController {

	@Autowired
	private ProductionLineService productionLineService;
	@Autowired
	private ProductionLineTechnologyService productionLineTechnologyService;
	
	/***
	 * -查询产线
	 * @param organizeCode
	 * @return
	 */
	@GetMapping("/list")
	public R<List<ProductionLine>> list(String organizeCode, Integer technologyWorkshop){
		R<List<ProductionLine>> result = new R<List<ProductionLine>>();
		if(organizeCode!=null && !"".equals(organizeCode) && technologyWorkshop!=null) {
			result.setCode(R.SUCCESS);
			result.setData(productionLineService.selectList(new EntityWrapper<ProductionLine>().eq("organize_code", organizeCode).eq("technology_workshop", technologyWorkshop).orderBy("serial_number")));
		}else {
			result.setCode(R.NULL_PARAMETER);
			result.setMsg("The parameter is empty.");
		}
		return result;
	}
	
	/***
	 * -添加产线
	 * @param productionLine
	 * @return
	 */
	@PostMapping("/save")
	public R<Boolean> save(ProductionLine productionLine){
		R<Boolean> result = new R<Boolean>();
		if(productionLine!=null) {
			productionLine.setSerialNumber(productionLineService.maxSerialNumber(productionLine.getOrganizeCode(),
					productionLine.getTechnologyWorkshop())+1);
			StringBuilder code = new StringBuilder(productionLine.getOrganizeCode()+productionLine.getTechnologyWorkshop());
			for(int i=0;i<3;i++) {
				if(code.length()<13) {
					code.append("0");
				} 
			}
			code.append(productionLine.getSerialNumber());
			productionLine.setProductionLineCode(code.toString());
			result.setCode(R.SUCCESS);
			result.setData(productionLineService.insert(productionLine));
		}else {
			result.setCode(R.NULL_PARAMETER);
			result.setData(false);
			result.setMsg("The parameter is empty.");
		}
		return result;
	}
	
	/***
	 * -修改产线
	 * @param productionLine
	 * @return
	 */
	@PostMapping("/update")
	public R<Boolean> update(String productionLineCode, String productionLineName){
		R<Boolean> result = new R<Boolean>();
		if(productionLineCode!=null && productionLineName!=null) {
			ProductionLine productionLine = new ProductionLine();
			productionLine.setProductionLineCode(productionLineCode);
			productionLine.setProductionLineName(productionLineName);
			result.setCode(R.SUCCESS);
			result.setData(productionLineService.updateById(productionLine));
		}else {
			result.setCode(R.NULL_PARAMETER);
			result.setData(false);
			result.setMsg("The parameter is empty.");
		}
		return result;
	}
	
	/***
	 * -删除产线
	 * @param productionLine
	 * @return
	 */
	@PostMapping("/remove")
	@Transactional
	public R<Boolean> remove(String productionLineCode){
		R<Boolean> result = new R<Boolean>();
		if(productionLineCode!=null && !"".equals(productionLineCode)) {
			boolean bl = productionLineService.deleteById(productionLineCode);
			if(bl == true) {
				//删除产线配置工艺
				int count = productionLineTechnologyService.selectCount(new EntityWrapper<ProductionLineTechnology>().eq("production_line_code", productionLineCode));
				if(count>0) {
					bl = productionLineTechnologyService.delete(new EntityWrapper<ProductionLineTechnology>().eq("production_line_code", productionLineCode));
				}	
			}
			result.setCode(R.SUCCESS);
			result.setData(bl);
		}else {
			result.setCode(R.NULL_PARAMETER);
			result.setData(false);
			result.setMsg("The parameter is empty.");
		}
		return result;
	}
}

