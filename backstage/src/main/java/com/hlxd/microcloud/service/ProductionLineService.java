package com.hlxd.microcloud.service;

import com.hlxd.microcloud.entity.ProductionLine;
import com.baomidou.mybatisplus.service.IService;

/***
 * - 工艺生产线  服务类
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
public interface ProductionLineService extends IService<ProductionLine> {
	/***
	 * -查询最大序号
	 * @param organizeCode
	 * @param technologyWorkshop
	 * @return
	 */
	Integer maxSerialNumber(String organizeCode, Integer technologyWorkshop);
}
