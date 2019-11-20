package com.hlxd.microcloud.mapper;

import com.hlxd.microcloud.entity.TechnologyStandard;
import com.hlxd.microcloud.entity.vo.TechnologyStandardVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/***
 * -生产工艺标准  Mapper 接口
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
public interface TechnologyStandardMapper extends BaseMapper<TechnologyStandard> {

	/***
	 * -展示生产工艺标准
	 * @param page
	 * @param technologyCode
	 * @return
	 */
	List<TechnologyStandardVo> list(Pagination page,@Param("technologyCode")String technologyCode);
}
