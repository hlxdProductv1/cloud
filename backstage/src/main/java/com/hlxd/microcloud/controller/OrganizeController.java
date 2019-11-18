package com.hlxd.microcloud.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hlxd.microcloud.entity.Organize;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.entity.vo.OrganizeTree;
import com.hlxd.microcloud.service.OrganizeService;

/**
 * <p>
 * -组织机构 前端控制器
 * </p>
 * 
 * @author admin
 * @since 2019-11-11
 */
@RestController
@RequestMapping("/organize")
public class OrganizeController {

	@Autowired
	private OrganizeService organizeService;

	@PostMapping("/saveBatch")
	@Transactional
	public R<Boolean> saveBatch(@RequestBody List<Organize> entityList) {
		R<Boolean> r = new R<Boolean>();
		if (entityList != null && entityList.size()>0) {
			boolean bl = true;
			for(Organize o : entityList) {
				o.setOrganizeCode(organizeService.uuid(o.getOrganizeType(), o.getSuperiorOrganizeCode()).toString());
				bl = organizeService.insert(o);
				if(bl == false) {
	    			try {
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					}catch (Exception e) {
						System.err.println("RollbackOnly");
					}
				}
			}
			r.setCode(R.SUCCESS);
			r.setData(bl);
		} else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg("The parameter is empty.");
		}
		return r;
	}
	
	/***
	 * -插入组织结构
	 * 
	 * @param entityList
	 * @return
	 */
	@PostMapping("/save")
	public R<Boolean> save(Organize organize) {
		R<Boolean> r = new R<Boolean>();
		if (organize != null) {
			organize.setOrganizeCode(organizeService.uuid(organize.getOrganizeType(), organize.getSuperiorOrganizeCode()).toString());
			r.setCode(R.SUCCESS);
			r.setData(organizeService.insert(organize));
		} else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg("The parameter is empty.");
		}
		return r;
	}

	/***
	 * -查询组织结构
	 * @param entityList
	 * @return
	 */
	@GetMapping("/list")
	public R<List<OrganizeTree>> list(String organizeCode){
		R<List<OrganizeTree>> r = new R<>();
		List<OrganizeTree> tree = organizeService.organizeTree(1, organizeCode, null);
		digui(tree);
		r.setCode(R.SUCCESS);
		r.setData(tree);
		return r;
	}
	
	/***
	 * -循环树形结构
	 * @param list
	 */
	private void digui(List<OrganizeTree> list) {
		List<OrganizeTree> tree = new ArrayList<OrganizeTree>();
        for (OrganizeTree c : list) {
        	tree = organizeService.organizeTree(null, null, c.getId());
            if (tree.size() > 0) {
            	c.setChildren(tree);
                digui(tree);
            }else {
            	break;
            }
        }
	}
	
	/***
	 * -修改组织机构
	 * @param organizeCode
	 * @param organizeName
	 * @return
	 */
	@PostMapping("/update")
	public R<Boolean> update(String organizeCode, String organizeName){
		R<Boolean> result = new R<>();
		if(organizeCode!=null && organizeName!=null && !"".equals(organizeCode) && !"".equals(organizeName)) {
			Organize entity = new Organize();
			entity.setOrganizeCode(organizeCode);
			entity.setOrganizeName(organizeName);
			result.setCode(R.SUCCESS);
			result.setData(organizeService.updateById(entity));
		}else {
			result.setCode(R.NULL_PARAMETER);
			result.setMsg("The parameter is empty.");
		}
		return result;
	}
	
	/***
	 * -删除组装机构
	 * @param organizeCode
	 * @return
	 */
	@PostMapping("/remove")
	public R<Boolean> remove(String organizeCode){
		R<Boolean> result = new R<>();
		if(organizeCode!=null && !"".equals(organizeCode)) {
			int count = organizeService.selectCount(new EntityWrapper<Organize>().eq("superior_organize_code", organizeCode));
			if(count != 0) {
				result.setCode(R.NO_PERMISSION);
				result.setMsg("prohibition operation");
			}else {
				result.setCode(R.SUCCESS);
				result.setData(organizeService.deleteById(organizeCode));
			}
		}else {
			result.setCode(R.NULL_PARAMETER);
			result.setMsg("The parameter is empty.");
		}
		return result;
	}
	
	@GetMapping("/uuid")
	public StringBuilder uuid(Integer organizeType, String superiorOrganizeCode) {
		return organizeService.uuid(organizeType, superiorOrganizeCode);
	}
}


