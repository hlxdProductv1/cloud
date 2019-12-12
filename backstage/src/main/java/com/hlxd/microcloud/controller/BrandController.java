package com.hlxd.microcloud.controller;

import java.io.IOException;
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
import com.hlxd.microcloud.entity.Brand;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.service.BrandService;
import com.hlxd.microcloud.util.ExcleData;

/***
 * -品牌表  前端控制器
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月21日
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

	@Autowired
	private BrandService brandService;
	
	/***
	 * -添加品牌
	 * @param entity
	 * @return
	 */
	@PostMapping("/save")
	public R<Boolean> save(String brandCode, String brandName, String organizeCode, Integer isProduce){
		R<Boolean> r = new R<Boolean>();
		if(!StringUtils.isEmpty(brandCode) && !StringUtils.isEmpty(brandName) && !StringUtils.isEmpty(organizeCode)) {
			Brand entity = new Brand(brandCode,brandName,organizeCode,isProduce);
			r.setCode(R.SUCCESS);
			r.setData(brandService.insert(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -删除品牌
	 * @param entity
	 * @return
	 */
	@PostMapping("/remove")
	public R<Boolean> remove(String brandCode){
		R<Boolean> r = new R<Boolean>();
		if(!StringUtils.isEmpty(brandCode)) {
			r.setCode(R.SUCCESS);
			r.setData(brandService.deleteById(brandCode));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -修改品牌
	 * @param entity
	 * @return
	 */
	@PostMapping("/update")
	public R<Boolean> update(String brandCode, String brandName, Integer isProduce){
		R<Boolean> r = new R<Boolean>();
		if(!StringUtils.isEmpty(brandCode)) {
			Brand entity = new Brand();
			entity.setBrandCode(brandCode);
			entity.setBrandName(brandName);
			entity.setIsProduce(isProduce);
			r.setCode(R.SUCCESS);
			r.setData(brandService.updateById(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -查询单个品牌
	 * @param entity
	 * @return
	 */
	@GetMapping("/get")
	public R<Brand> get(String brandCode){
		R<Brand> r = new R<Brand>();
		if(!StringUtils.isEmpty(brandCode)) {
			r.setCode(R.SUCCESS);
			r.setData(brandService.selectById(brandCode));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -查询工厂下的所有品牌
	 * @param entity
	 * @return
	 */
	@GetMapping("/list")
	public R<Page<Brand>> list(Integer current, Integer size, String organizeCode, String brandName, Integer isProduce){
		R<Page<Brand>> r = new R<Page<Brand>>();
		if(current!=null && size!=null && !StringUtils.isEmpty(organizeCode)) {
			Wrapper<Brand> wrapper = new EntityWrapper<Brand>().eq("organize_code", organizeCode);
			if(!StringUtils.isEmpty(brandName)) {
				wrapper.and().like("brand_name", brandName);
			}
			if(isProduce!=null) {
				wrapper.and().eq("is_produce", isProduce);
			}
			r.setCode(R.SUCCESS);
			r.setData(brandService.selectPage(new Page<Brand>(current, size),wrapper));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}

	/***
	 * -查询工厂下的所有品牌
	 * @param entity
	 * @return
	 */
	@GetMapping("/select")
	public R<List<Brand>> select(String organizeCode){
		R<List<Brand>> r = new R<List<Brand>>();
		if(!StringUtils.isEmpty(organizeCode)) {
			Wrapper<Brand> wrapper = new EntityWrapper<Brand>().eq("organize_code", organizeCode).eq("is_produce", 1);
			r.setCode(R.SUCCESS);
			r.setData(brandService.selectList(wrapper));
		}else {
			r.setCode(R.NULL_PARAMETER);
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
        String fileName = "cigarette.xls";
        String[] headers = { "烟卷规格编码", "烟卷规格名称", "是否在生产0/1"};
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
				List<Brand> entityList = new ArrayList<Brand>();
				if(list!=null && list.size()>0) {
					for(String[] s: list) {
						entityList.add(new Brand(excleData.isENum(s[0]),s[1],organizeCode, Double.valueOf(s[2]).intValue()));
					}
				}
	            if(entityList.size()>0) {
	            	r.setData(brandService.insertBatch(entityList));
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

