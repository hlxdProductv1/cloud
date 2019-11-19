package com.hlxd.microcloud.service;

import com.hlxd.microcloud.entity.ProductionLineTechnology;
import com.hlxd.microcloud.entity.vo.ProductionLineVo;
import java.util.List;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 工艺生产线工艺  服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-13
 */
public interface ProductionLineTechnologyService extends IService<ProductionLineTechnology> {
	List<ProductionLineVo> productionLine(String productionLineCode);
}
