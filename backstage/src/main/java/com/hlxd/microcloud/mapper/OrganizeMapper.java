package com.hlxd.microcloud.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hlxd.microcloud.entity.Organize;
import com.hlxd.microcloud.entity.vo.OrganizeTreeVo;

/***
 * -组织机构 Mapper 接口
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月18日
 */
public interface OrganizeMapper extends BaseMapper<Organize> {
	
	/***
	 * -查询组织机构树
	 * @param organizeType
	 * @param organizeCode
	 * @param superiorOrganizeCode
	 * @return
	 */
	List<OrganizeTreeVo> organizeTree(@Param("organizeType")Integer organizeType, @Param("organizeCode")String organizeCode, @Param("superiorOrganizeCode")String superiorOrganizeCode);
	/***
	 * -空缺id
	 * @param organizeType 组织类型
	 * @param superiorOrganizeCode 上级组织id
	 * @return
	 */
	Integer vacancyOrganizeCode(@Param("organizeType")Integer organizeType, @Param("size") Integer size, @Param("superiorOrganizeCode")String superiorOrganizeCode);
	/***
	 * -最大id
	 * @param organizeType 组织类型
	 * @param superiorOrganizeCode 上级组织id
	 * @return
	 */
	Integer maxOrganizeCode(@Param("organizeType")Integer organizeType, @Param("size") Integer size,@Param("superiorOrganizeCode") String superiorOrganizeCode);
}
