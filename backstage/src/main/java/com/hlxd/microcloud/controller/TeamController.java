package com.hlxd.microcloud.controller;

import java.util.Calendar;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hlxd.microcloud.entity.Team;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.service.TeamService;

/***
 * -班组表  前端控制器
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月21日
 */
@RestController
@RequestMapping("/team")
public class TeamController {
	
	@Autowired
	private TeamService teamService;
	
	/***
	 * -添加班组
	 * @param entity
	 * @return
	 */
	@PostMapping("/save")
	public R<Boolean> save(String organizeCode, String teamName){
		R<Boolean> r = new R<Boolean>();
		if(!StringUtils.isEmpty(teamName) && !StringUtils.isEmpty(organizeCode)) {
			Team entity = new Team();
			entity.setOrganizeCode(organizeCode);
			StringBuilder code = new StringBuilder(entity.getOrganizeCode());
			code.append((Calendar.getInstance().getTimeInMillis()+"").substring(9));
			code.append(((int)(Math.random()*900 + 100))).toString();
			entity.setTeamCode(code.toString());
			entity.setTeamName(teamName);
			r.setCode(R.SUCCESS);
			r.setData(teamService.insert(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -删除班组
	 * @param entity
	 * @return
	 */
	@PostMapping("/remove")
	public R<Boolean> remove(String teamCode){
		R<Boolean> r = new R<Boolean>();
		if(!StringUtils.isEmpty(teamCode)) {
			r.setCode(R.SUCCESS);
			r.setData(teamService.deleteById(teamCode));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -修改班组
	 * @param entity
	 * @return
	 */
	@PostMapping("/update")
	public R<Boolean> update(String teamCode, String teamName){
		R<Boolean> r = new R<Boolean>();
		if(!StringUtils.isEmpty(teamCode) && !StringUtils.isEmpty(teamName)) {
			Team entity = new Team();
			entity.setTeamCode(teamCode);
			entity.setTeamName(teamName);
			r.setCode(R.SUCCESS);
			r.setData(teamService.updateById(entity));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setData(false);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
	
	/***
	 * -查询班组
	 * @param entity
	 * @return
	 */
	@GetMapping("/list")
	public R<List<Team>> list(String organizeCode){
		R<List<Team>> r = new R<List<Team>>();
		if(!StringUtils.isEmpty(organizeCode)) {
			r.setCode(R.SUCCESS);
			r.setData(teamService.selectList(new EntityWrapper<Team>().eq("organize_code", organizeCode)));
		}else {
			r.setCode(R.NULL_PARAMETER);
			r.setMsg(R.NULL_PARAMETER_MSG);
		}
		return r;
	}
}

