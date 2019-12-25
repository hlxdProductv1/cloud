package com.hlxd.microcloud.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import com.baomidou.mybatisplus.plugins.Page;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.entity.Technology;
import com.hlxd.microcloud.entity.TechnologyStandard;
import com.hlxd.microcloud.entity.TechnologyStandardValue;
import com.hlxd.microcloud.entity.vo.TechnologyStandardVo;
import com.hlxd.microcloud.service.TechnologyService;
import com.hlxd.microcloud.service.TechnologyStandardService;
import com.hlxd.microcloud.service.TechnologyStandardValueService;
import com.hlxd.microcloud.util.ExcleData;

/***
 * - 生产工艺标准  前端控制器
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月20日
 */
@RestController
@RequestMapping("/technologyStandardValue")
public class TechnologyStandardValueController {
	
	@Autowired
	private TechnologyStandardService technologyStandardService;
	@Autowired
	private TechnologyService technologyService;
	@Autowired
	private TechnologyStandardValueService technologyStandardValueService;
	
	
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
			r.setData(technologyStandardValueService.list(new Page<TechnologyStandardVo>(current, size), organizeCode, brandCode,
					standardName, technologyCode));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -添加生产工艺标准值
	 * @param entity
	 * @return
	 */
	@PostMapping("/save")
	public R<Boolean> save(TechnologyStandardValue entity){
		R<Boolean> r = new R<>();
		if(entity!=null) {
			r.setCode(R.SUCCESS);
			r.setData(technologyStandardValueService.insert(entity));
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
	public R<Boolean> remove(String id){
		R<Boolean> r = new R<>();
		if(!StringUtils.isEmpty(id)) {
			r.setCode(R.SUCCESS);
			r.setData(technologyStandardValueService.deleteById(id));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -修改生产工艺标准值
	 * @param entity
	 * @return
	 */
	@PostMapping("/update")
	public R<Boolean> updateValue(TechnologyStandardValue entity){
		R<Boolean> r = new R<>();
		if(entity!=null) {
			r.setCode(R.SUCCESS);
			r.setData(technologyStandardValueService.updateById(entity));
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
	 * -导入生产标准值
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
				List<TechnologyStandardValue> entityValueList = new ArrayList<>();
				if(list!=null && list.size()>0) {
					List<Technology> technologyList = technologyService.selectList(new EntityWrapper<Technology>().eq("organize_code", organizeCode));
					List<TechnologyStandard> tslist = technologyStandardService.selectList(null);
					TechnologyStandard entity = null;
					TechnologyStandardValue entityValue = null;
					StringBuilder code = null;
					for(String[] s: list) {
						if("".equals(s[0])) {
							break;
						}
						entityValue = new TechnologyStandardValue();
						code = new StringBuilder(organizeCode);
						code.append(((int)(Math.random()*900 + 100))).toString();
						//是否存在标准
						if(tslist!=null && tslist.size()>0) {
							for(TechnologyStandard t:tslist) {
								if(t.getStandardName().equals(s[0])) {
									entityValue.setStandardCode(t.getStandardCode());
								}
							}
						}
						if(entityValue.getStandardCode()==null || entityValue.getStandardCode().equals("")){
							//标准不存在是否导入重复
							if(entityList!=null && entityList.size()>0) {
								for(TechnologyStandard ts:entityList) {//存在空值
									if(ts.getStandardName().equals(s[0])) {
										entityValue.setStandardCode(ts.getStandardCode());
									}
								}
							}
							//导入不重复新建标准
							if(entityValue.getStandardCode()==null || entityValue.getStandardCode().equals("")){
								entity = new TechnologyStandard();
								if(technologyList!=null && technologyList.size()>0) {
									for(Technology t : technologyList) {
										if(t.getTechnologyName().equals(s[1])) {
											entity.setTechnologyCode(t.getTechnologyCode());
											break;
										}
									}
								}
								code = new StringBuilder(organizeCode);
								code.append(((int)(Math.random()*900 + 100))).toString();
								entityValue.setStandardCode(code.toString());
								entity.setStandardCode(code.toString());
								entity.setStandardName(s[0]);
								entity.setStandardType(Double.valueOf(s[3]).intValue());
								entity.setUnit(s[4]);
								entityList.add(entity);
							}
						}
						//标准值
						entityValue.setId(UUID.randomUUID().toString());
						entityValue.setCigaretteCode(s[2]);
						entityValue.setStandardValue(new BigDecimal(s[5]));
						entityValue.setMaximum(new BigDecimal(s[6]));
						entityValue.setMinimum(new BigDecimal(s[7]));
						entityValue.setFraction(new BigDecimal(s[8]));
						entityValueList.add(entityValue);
					}
				}
	            if(entityValueList.size()>0) {
	            	boolean bl = true;
	            	if(entityList.size()>0) {
	            		bl = technologyStandardService.insertBatch(entityList);
	            	}
	            	if(bl == true) {
	            		bl = technologyStandardValueService.insertBatch(entityValueList);	
	            	}
	            	r.setData(bl);
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
