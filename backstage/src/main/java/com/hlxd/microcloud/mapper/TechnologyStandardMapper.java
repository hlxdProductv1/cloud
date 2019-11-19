package com.hlxd.microcloud.mapper;

import com.hlxd.microcloud.entity.TechnologyStandard;
import com.hlxd.microcloud.entity.vo.TechnologyStandardVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 生产工艺标准  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-11-14
 */
public interface TechnologyStandardMapper extends BaseMapper<TechnologyStandard> {

	List<TechnologyStandardVo> list(Pagination page,@Param("technologyCode")String technologyCode);
}
