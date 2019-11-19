package com.hlxd.microcloud.service;

import com.hlxd.microcloud.entity.TechnologyStandard;
import com.hlxd.microcloud.entity.vo.TechnologyStandardVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 生产工艺标准  服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-14
 */
public interface TechnologyStandardService extends IService<TechnologyStandard> {

	Page<TechnologyStandardVo> list(Page<TechnologyStandardVo> page,String technologyCode);
}
