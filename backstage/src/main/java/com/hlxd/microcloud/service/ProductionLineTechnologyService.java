package com.hlxd.microcloud.service;

import com.hlxd.microcloud.entity.ProductionLineTechnology;
import com.hlxd.microcloud.entity.vo.ProductionLineVo;
import java.util.List;
import com.baomidou.mybatisplus.service.IService;

/***
 * -工艺生产线工艺  服务类
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
public interface ProductionLineTechnologyService extends IService<ProductionLineTechnology> {
	
	/***
	 * -展示生产工艺
	 * @param productionLineCode
	 * @return
	 */
	List<ProductionLineVo> productionLine(String productionLineCode);
	/***
	 * -查询最大编号
	 * @param productionLineCode
	 * @return
	 */
	Integer maxSerialNumber(String productionLineCode);
}
