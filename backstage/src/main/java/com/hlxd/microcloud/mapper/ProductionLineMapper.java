package com.hlxd.microcloud.mapper;

import com.hlxd.microcloud.entity.ProductionLine;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/***
 * -工艺生产线  Mapper 接口
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
public interface ProductionLineMapper extends BaseMapper<ProductionLine> {
	/***
	 * -查询最大序号
	 * @param organizeCode
	 * @param technologyWorkshop
	 * @return
	 */
	Integer maxSerialNumber(@Param("organizeCode")String organizeCode, @Param("technologyWorkshop")Integer technologyWorkshop);
}
