package com.hlxd.microcloud.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hlxd.microcloud.entity.Organize;
import com.hlxd.microcloud.entity.vo.OrganizeTreeVo;
import com.hlxd.microcloud.mapper.OrganizeMapper;
import com.hlxd.microcloud.service.OrganizeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * -组织机构 服务实现类
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
@Service
public class OrganizeServiceImpl extends ServiceImpl<OrganizeMapper, Organize> implements OrganizeService {

	@Autowired
	private OrganizeMapper organizeMapper;
	
	@Override
	public List<OrganizeTreeVo> organizeTree(Integer organizeType,String organizeCode, String superiorOrganizeCode) {
		return organizeMapper.organizeTree(organizeType, organizeCode, superiorOrganizeCode);
	}
	
	@Override
	public StringBuilder uuid(String superiorOrganizeCode) {
		Integer organizeType = 1;
		if(superiorOrganizeCode!=null && !"".equals(superiorOrganizeCode)) {
			organizeType = organizeMapper.superiororganizeType(superiorOrganizeCode)+1;
		}
		Integer id = organizeMapper.vacancyOrganizeCode(organizeType, organizeType+1, superiorOrganizeCode);
		if(id == null) {
			id = organizeMapper.maxOrganizeCode(organizeType, organizeType+1, superiorOrganizeCode);
		}
		StringBuilder builder = new StringBuilder();
		if(id != null) {
			builder.append(id);
		}else {
			builder.append("1");
		}
		if(organizeType!=1) {
			for(int i=0;i<organizeType;i++) {
				if(builder.length()<(organizeType+1)) {
					builder.insert(0,"0");
				}
			}
		}
		if(superiorOrganizeCode!=null && !"".equals(superiorOrganizeCode)) {
			builder.insert(0,superiorOrganizeCode);
		}
		return builder;
	}

	@Override
	public Integer superiororganizeType(String superiorOrganizeCode) {
		return organizeMapper.superiororganizeType(superiorOrganizeCode);
	}

}
