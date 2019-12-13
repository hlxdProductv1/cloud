package com.hlxd.microcloud.controller;

import org.apache.commons.lang.StringUtils;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import com.hlxd.microcloud.entity.vo.OrganizeTreeVo;
import com.hlxd.microcloud.service.OrganizeService;

/***
 * -组织机构 前端控制器
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
@RestController
@RequestMapping("/organize")
public class OrganizeController {

	@Autowired
	private OrganizeService organizeService;
	
	/***
	 * -批量添加组织结构
	 * @param entityList
	 * @return
	 */
	@PostMapping("/saveBatch")
	@Transactional
	@CacheEvict(value="organize",allEntries=true)
	public R<Boolean> saveBatch(@RequestBody List<Organize> entityList) {
		R<Boolean> r = new R<Boolean>();
		if (entityList != null && entityList.size()>0) {
			boolean bl = true;
			Iterator<Organize> iterator = entityList.iterator();
			while(iterator.hasNext()) {
				Organize item = iterator.next();
				item.setOrganizeCode(organizeService.uuid(item.getSuperiorOrganizeCode()).toString());
				bl = organizeService.insert(item);
				if(bl == false) {
	    			try {
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			r.setCode(R.SUCCESS);
			r.setData(bl);
		} else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -插入组织结构
	 * @param entityList
	 * @return
	 */
	@PostMapping("/save")
	@CacheEvict(value="organize",allEntries=true)
	public R<Boolean> save(Organize organize) {
		R<Boolean> r = new R<Boolean>();
		if (organize != null) {
			organize.setOrganizeCode(organizeService.uuid(organize.getSuperiorOrganizeCode()).toString());
			r.setCode(R.SUCCESS);
			r.setData(organizeService.insert(organize));
		} else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}

	/***
	 * -查询组织结构
	 * @param entityList
	 * @return
	 */
	@GetMapping("/list")
	@Cacheable(value="organize")
	public R<List<OrganizeTreeVo>> list(String organizeCode){
		R<List<OrganizeTreeVo>> r = new R<>();
		List<OrganizeTreeVo> tree = organizeService.organizeTree(0, organizeCode, null);
		digui(tree);
		r.setCode(R.SUCCESS);
		r.setData(tree);
		return r;
	}
	
	/***
	 * -循环树形结构
	 * @param list
	 */
	private void digui(List<OrganizeTreeVo> list) {
		List<OrganizeTreeVo> tree = null;
		OrganizeTreeVo o = null;
        for (int i=0,length=list.size();i<length;i++) {
        	o = list.get(i);
        	tree = organizeService.organizeTree(null, null, o.getId());
            if (tree!=null && tree.size() > 0) {
            	o.setChildren(tree);
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
	@CacheEvict(value="organize",allEntries=true)
	public R<Boolean> update(String organizeCode, String organizeName,String superiorOrganizeCode){
		R<Boolean> result = new R<>();
		if(!StringUtils.isEmpty(organizeCode) && !StringUtils.isEmpty(organizeName)) {
			Organize entity = new Organize();
			entity.setOrganizeCode(organizeCode);
			entity.setOrganizeName(organizeName);
			result.setCode(R.SUCCESS);
			Integer count = organizeService.selectCount(new EntityWrapper<Organize>().eq("organize_code", organizeCode));
			if(count!=null && count!=0) {
				result.setData(organizeService.updateById(entity));
			}else {
				entity.setSuperiorOrganizeCode(superiorOrganizeCode);
				entity.setOrganizeType(organizeService.superiororganizeType(superiorOrganizeCode)+1);
				result.setData(organizeService.insert(entity));
			}
		}else {
			result.setCode(R.NULL_PARAMETER);
			result.setData(false);
			result.setMsg(R.NULL_PARAMETER_MSG);
		}
		return result;
	}
	
	/***
	 * -删除组装机构
	 * @param organizeCode
	 * @return
	 */
	@PostMapping("/remove")
	@CacheEvict(value="organize",allEntries=true)
	public R<Boolean> remove(String organizeCode){
		R<Boolean> result = new R<>();
		if(!StringUtils.isEmpty(organizeCode)) {
			int count = organizeService.selectCount(new EntityWrapper<Organize>().eq("superior_organize_code", organizeCode));
			if(count != 0) {
				result.setCode(R.NO_PERMISSION);
				result.setData(false);
				result.setMsg(R.NO_PERMISSION_MSG);
			}else {
				result.setCode(R.SUCCESS);
				result.setData(organizeService.deleteById(organizeCode));
			}
		}else {
			result.setCode(R.NULL_PARAMETER);
			result.setData(false);
			result.setMsg(R.NULL_PARAMETER_MSG);
		}
		return result;
	}
	
	/***
	 * -获取新组织机构UUID
	 * @param organizeType
	 * @param superiorOrganizeCode
	 * @return
	 */
	@GetMapping("/uuid")
	public StringBuilder uuid(String superiorOrganizeCode) {
		return organizeService.uuid(superiorOrganizeCode);
	}
}


