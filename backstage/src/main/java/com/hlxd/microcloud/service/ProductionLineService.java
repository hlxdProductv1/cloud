package com.hlxd.microcloud.service;

import com.hlxd.microcloud.entity.ProductionLine;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 工艺生产线  服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-12
 */
public interface ProductionLineService extends IService<ProductionLine> {
	Integer maxSerialNumber(String organizeCode, Integer technologyWorkshop);
}
