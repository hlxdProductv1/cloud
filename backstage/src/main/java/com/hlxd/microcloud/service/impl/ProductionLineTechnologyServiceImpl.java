package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.entity.ProductionLineTechnology;
import com.hlxd.microcloud.entity.vo.ProductionLineVo;
import com.hlxd.microcloud.mapper.ProductionLineTechnologyMapper;
import com.hlxd.microcloud.service.ProductionLineTechnologyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * -工艺生产线工艺  服务实现类
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
@Service
public class ProductionLineTechnologyServiceImpl extends ServiceImpl<ProductionLineTechnologyMapper, ProductionLineTechnology> implements ProductionLineTechnologyService {

	@Autowired
	private ProductionLineTechnologyMapper productionLineTechnologyMapper;
	
	@Override
	public List<ProductionLineVo> productionLine(String productionLineCode) {
		return productionLineTechnologyMapper.productionLine(productionLineCode);
	}

	@Override
	public Integer maxSerialNumber(String productionLineCode) {
		// TODO Auto-generated method stub
		return productionLineTechnologyMapper.maxSerialNumber(productionLineCode);
	}

}
