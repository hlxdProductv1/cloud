package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.entity.ProductionLine;
import com.hlxd.microcloud.mapper.ProductionLineMapper;
import com.hlxd.microcloud.service.ProductionLineService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * -工艺生产线  服务实现类
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
@Service
public class ProductionLineServiceImpl extends ServiceImpl<ProductionLineMapper, ProductionLine> implements ProductionLineService {

	@Autowired
	private ProductionLineMapper productionLineMapper;
	
	@Override
	public Integer maxSerialNumber(String organizeCode, Integer technologyWorkshop) {
		Integer number = productionLineMapper.maxSerialNumber(organizeCode, technologyWorkshop);
		number = number!=null?number:0;
		return number;
	}

}
