package com.hlxd.microcloud.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.entity.Technology;
import com.hlxd.microcloud.entity.TechnologyStandard;
import com.hlxd.microcloud.entity.vo.TechnologyStandardVo;
import com.hlxd.microcloud.service.TechnologyService;
import com.hlxd.microcloud.service.TechnologyStandardService;
import com.hlxd.microcloud.util.ExcleData;

/***
 * - 生产工艺标准  前端控制器
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
@RestController
@RequestMapping("/technologyStandard")
public class TechnologyStandardController {
	
	@Autowired
	private TechnologyStandardService technologyStandardService;
	@Autowired
	private TechnologyService technologyService;
	
	/***
	 * -查询单个生产工艺标准
	 * @param standardCode
	 * @return
	 */
	@GetMapping("/get")
	public R<TechnologyStandard> get(String standardCode){
		R<TechnologyStandard> r = new R<>();
		if(!StringUtils.isEmpty(standardCode)) {
			r.setCode(R.SUCCESS);
			TechnologyStandard entity = technologyStandardService.selectById(standardCode);
			r.setData(entity);
			if(entity==null) {
				r.setMsg(R.NULL_QUERY);
			}
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -查询系列生产工艺标准
	 * @param current
	 * @param size
	 * @param technologyCode
	 * @return
	 */
	@GetMapping("/list")
	public R<Page<TechnologyStandardVo>> list(Integer current, Integer size, String organizeCode, String brandCode,
			String standardName, String technologyCode){
		R<Page<TechnologyStandardVo>> r = new R<>();
		if(current!=null && size!=null && !StringUtils.isEmpty(organizeCode)) {
			r.setCode(R.SUCCESS);
			r.setData(technologyStandardService.list(new Page<TechnologyStandardVo>(current, size), organizeCode, brandCode,
					standardName, technologyCode));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -工艺标准下拉列表
	 * @param cigaretteCode
	 * @param technologyCode
	 * @return
	 */
	@GetMapping("/select")
	public R<List<TechnologyStandard>> select(String cigaretteCode, String technologyCode){
		R<List<TechnologyStandard>> r = new R<>();
		if(!StringUtils.isEmpty(cigaretteCode) || !StringUtils.isEmpty(technologyCode)) {
			Wrapper<TechnologyStandard> wrapper = new EntityWrapper<TechnologyStandard>();
			if(!StringUtils.isEmpty(cigaretteCode)) {
				wrapper.and().eq("cigarette_code", cigaretteCode);
			}
			if(!StringUtils.isEmpty(technologyCode)){
				wrapper.and().eq("technology_code", technologyCode);
			}
			r.setCode(R.SUCCESS);
			r.setData(technologyStandardService.selectList(wrapper));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -添加生产工艺标准
	 * @param entity
	 * @return
	 */
	@PostMapping("/save")
	public R<Boolean> save(TechnologyStandard entity){
		R<Boolean> r = new R<>();
		if(entity!=null) {
			StringBuilder code = new StringBuilder(entity.getCigaretteCode());
			code.append(entity.getTechnologyCode());
			code.append(((int)(Math.random()*900 + 100))).toString();
			entity.setStandardCode(code.toString());
			r.setCode(R.SUCCESS);
			r.setData(technologyStandardService.insert(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -删除生产工艺标准
	 * @param standardCode
	 * @return
	 */
	@PostMapping("/remove")
	public R<Boolean> remove(String standardCode){
		R<Boolean> r = new R<>();
		if(!StringUtils.isEmpty(standardCode)) {
			r.setCode(R.SUCCESS);
			r.setData(technologyStandardService.deleteById(standardCode));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -修改生产工艺标准
	 * @param entity
	 * @return
	 */
	@PostMapping("/update")
	public R<Boolean> update(TechnologyStandard entity){
		R<Boolean> r = new R<>();
		if(entity!=null) {
			r.setCode(R.SUCCESS);
			r.setData(technologyStandardService.updateById(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -导出模板
	 * @throws IOException 
	 */
	@GetMapping("/export")
	public void export(HttpServletResponse response) throws IOException {
        String fileName = "TechnologyStandard.xls";
        String[] headers = { "指标名称", "所属工艺", "卷烟规格编码","指标类型 1.定量2.定性","单位","标准值","最大值","最小值","分数"};
        ExcleData excleData = new ExcleData();
        excleData.export(headers, fileName, response);
	}
	
	/***
	 * -导入品牌
	 * @throws IOException 
	 */
	@PostMapping("/import")
	public R<Boolean> importBrand(@RequestParam("file") MultipartFile file,String organizeCode){
		R<Boolean> r = new R<>();
		if(!StringUtils.isEmpty(organizeCode) && !file.isEmpty()) {
			try {
				ExcleData excleData = new ExcleData();
				List<String[]> list = excleData.getExcelData(file);
				List<TechnologyStandard> entityList = new ArrayList<>();
				if(list!=null && list.size()>0) {
					List<Technology> technologyList = technologyService.selectList(new EntityWrapper<Technology>().eq("organize_code", organizeCode));
					TechnologyStandard entity = null;
					for(String[] s: list) {
						entity = new TechnologyStandard();
						if(technologyList!=null && technologyList.size()>0) {
							for(Technology t : technologyList) {
								if(t.getTechnologyName().equals(s[1])) {
									entity.setTechnologyCode(t.getTechnologyCode());
									break;
								}
							}
						}
						entity.setStandardName(s[0]);
						entity.setCigaretteCode(s[2]);
						entity.setStandardType(Double.valueOf(s[3]).intValue());
						entity.setUnit(s[4]);
						entity.setStandardValue(new BigDecimal(s[5]));
						entity.setMaximum(new BigDecimal(s[6]));
						entity.setMinimum(new BigDecimal(s[7]));
						entity.setFraction(new BigDecimal(s[8]));
						entityList.add(entity);
					}
				}
	            if(entityList.size()>0) {
	            	r.setData(technologyStandardService.insertBatch(entityList));
	            }else {
	            	r.setData(false);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			r.setCode(R.SUCCESS);
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
}
