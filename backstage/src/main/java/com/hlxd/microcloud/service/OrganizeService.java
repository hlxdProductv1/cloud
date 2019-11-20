package com.hlxd.microcloud.service;

import java.util.List;
import com.baomidou.mybatisplus.service.IService;
import com.hlxd.microcloud.entity.Organize;
import com.hlxd.microcloud.entity.vo.OrganizeTree;

/***
 * -组织机构 服务类
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
public interface OrganizeService extends IService<Organize> {
	/***
	 * -查询组织机构树
	 * @param organizeType
	 * @param organizeCode
	 * @param superiorOrganizeCode
	 * @return
	 */
	List<OrganizeTree> organizeTree(Integer organizeType, String organizeCode, String superiorOrganizeCode);
	/***
	 * -组织机构id生成规则
	 * @param type 组织类型
	 * @param fid 上级id 自下而上顺序 1:fid 2:ffid
	 * @param id 空缺id
	 * @return
	 */
	StringBuilder uuid(Integer organizeType, String superiorOrganizeCode);
}
