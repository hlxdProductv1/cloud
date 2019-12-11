package com.hlxd.microcloud.mapper;

import com.hlxd.microcloud.entity.ProductionLineTechnology;
import com.hlxd.microcloud.entity.vo.ProductionLineVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/***
 * -工艺生产线工艺  Mapper 接口
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
public interface ProductionLineTechnologyMapper extends BaseMapper<ProductionLineTechnology> {

	/***
	 * -展示生产工艺
	 * @param productionLineCode
	 * @return
	 */
	List<ProductionLineVo> productionLine(@Param("productionLineCode")String productionLineCode);
	
	/***
	 * -查询最大编号
	 * @param productionLineCode
	 * @return
	 */
	Integer maxSerialNumber(@Param("productionLineCode")String productionLineCode);
}
