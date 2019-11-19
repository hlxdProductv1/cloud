package com.hlxd.microcloud.mapper;

import com.hlxd.microcloud.entity.ProductionLine;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 工艺生产线  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-11-12
 */
public interface ProductionLineMapper extends BaseMapper<ProductionLine> {
	Integer maxSerialNumber(@Param("organizeCode")String organizeCode, @Param("technologyWorkshop")Integer technologyWorkshop);
}
