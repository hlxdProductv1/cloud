package com.hlxd.microcloud.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hlxd.microcloud.entity.Organize;
import com.hlxd.microcloud.entity.vo.OrganizeTree;
import com.hlxd.microcloud.mapper.OrganizeMapper;
import com.hlxd.microcloud.service.OrganizeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 组织机构 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-11-11
 */
@Service
public class OrganizeServiceImpl extends ServiceImpl<OrganizeMapper, Organize> implements OrganizeService {

	@Autowired
	private OrganizeMapper organizeMapper;
	
	@Override
	public List<OrganizeTree> organizeTree(Integer organizeType,String organizeCode, String superiorOrganizeCode) {
		return organizeMapper.organizeTree(organizeType, organizeCode, superiorOrganizeCode);
	}
	
	/***
	 * -可解读性id
	 */
	@Override
	public StringBuilder uuid(Integer organizeType, String superiorOrganizeCode) {
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
		for(int i=0;i<organizeType;i++) {
			if(builder.length()<(organizeType+1)) {
				builder.insert(0,"0");
			}
		}
		builder.insert(0,superiorOrganizeCode);
		return builder;
	}

}
