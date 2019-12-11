package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.entity.TechnologyStandard;
import com.hlxd.microcloud.entity.vo.TechnologyStandardVo;
import com.hlxd.microcloud.mapper.TechnologyStandardMapper;
import com.hlxd.microcloud.service.TechnologyStandardService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * -生产工艺标准  服务实现类
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
@Service
public class TechnologyStandardServiceImpl extends ServiceImpl<TechnologyStandardMapper, TechnologyStandard> implements TechnologyStandardService {

	@Autowired
	private TechnologyStandardMapper technologyStandardMapper;
	
	@Override
	public Page<TechnologyStandardVo> list(Page<TechnologyStandardVo> page, String organizeCode, String brandCode,
			String standardName, String technologyCode) {
		return page.setRecords(technologyStandardMapper.list(page,organizeCode,brandCode,standardName,technologyCode));
	}

}
