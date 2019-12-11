package com.hlxd.microcloud.feign;

import com.baomidou.mybatisplus.plugins.Page;
import com.hlxd.microcloud.entity.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/12/617:12
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@FeignClient(value = "backstage-server")
public interface InitDataFeign {

    @RequestMapping("/brand/list")
    public List<Brand> getAllBrand();


    @RequestMapping("/equipment/list")
    public List<Equipment> getAllEquipment();


    @RequestMapping("/organize/list")
    public List<Organize> getAllOrganize();


    @RequestMapping("/productionLine/list")
    public List<ProductionLine> getAllProductionLine();


    @RequestMapping("/shifts/list")
    public List<Shifts> getAllShifts();


    @RequestMapping("/team/list")
    public List<Team> getAllTeam();


    @RequestMapping("/technologyStandard/list")
    public List<TechnologyStandard> getAllTechnologyStandard();


    @RequestMapping("/workshopSection/list")
    public List<WorkshopSection> getAllWorkshopSection();

}
