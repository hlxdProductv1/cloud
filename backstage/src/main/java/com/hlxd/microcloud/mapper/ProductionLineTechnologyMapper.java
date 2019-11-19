package com.hlxd.microcloud.mapper;

import com.hlxd.microcloud.entity.ProductionLineTechnology;
import com.hlxd.microcloud.entity.vo.ProductionLineVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 工艺生产线工艺  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-11-13
 */
public interface ProductionLineTechnologyMapper extends BaseMapper<ProductionLineTechnology> {

	List<ProductionLineVo> productionLine(@Param("productionLineCode")String productionLineCode);
}
