package com.hlxd.microcloud.service;

import com.hlxd.microcloud.entity.TechnologyStandard;
import com.hlxd.microcloud.entity.vo.TechnologyStandardVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/***
 * -生产工艺标准  服务类
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
public interface TechnologyStandardService extends IService<TechnologyStandard> {

	/***
	 * -展示生产工艺标准
	 * @param page
	 * @param technologyCode
	 * @return
	 */
	Page<TechnologyStandardVo> list(Page<TechnologyStandardVo> page,String organizeCode, String brandCode,
			String standardName, String technologyCode);
}
