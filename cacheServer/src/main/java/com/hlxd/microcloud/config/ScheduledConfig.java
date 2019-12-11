package com.hlxd.microcloud.config;

import com.hlxd.microcloud.entity.*;
import com.hlxd.microcloud.feign.InitDataFeign;
import com.hlxd.microcloud.service.IConstantService;
import com.hlxd.microcloud.util.EHCacheUtil;
import com.hlxd.microcloud.util.JedisPoolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/12/511:10
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Component
public class ScheduledConfig {
    @Autowired
    private IConstantService iConstantService;

    @Autowired
    private InitDataFeign initDataFeign;


    /**
     *
     * 初始化配置项缓存
     *
     */
    @PostConstruct
    public void initCache(){
        List<Constant> constants = iConstantService.getConstant();//获取配置数据
        /*List<Brand> brands = initDataFeign.getAllBrand();
        List<Equipment> equipments  = initDataFeign.getAllEquipment();
        List<Organize> organizes = initDataFeign.getAllOrganize();
        List<ProductionLine> productionLines = initDataFeign.getAllProductionLine();
        List<Shifts> shifts = initDataFeign.getAllShifts();
        List<Team> teams = initDataFeign.getAllTeam();
        List<TechnologyStandard> technologyStandards = initDataFeign.getAllTechnologyStandard();
        List<WorkshopSection> workshopSections = initDataFeign.getAllWorkshopSection();*/
        JedisPoolUtils.delAllValue();
        for(Constant constant:constants){
            JedisPoolUtils.setPool(0,constant.getName(),constant.getValue());//存放配置文件数据
        }
        /*for(Brand brand:brands){
            JedisPoolUtils.setPool(1,brand.getBrandName(),brand.getBrandCode());
        }
        for(Equipment equipment:equipments){
            JedisPoolUtils.setPool(2,equipment.getEquipmentName(),equipment.getEquipmentCode());
        }
        for(Organize organize:organizes){
            JedisPoolUtils.setPool(3,organize.getOrganizeName(),organize.getOrganizeCode());
        }
        for(ProductionLine productionLine:productionLines){
            JedisPoolUtils.setPool(4,productionLine.getProductionLineName(),productionLine.getProductionLineCode());
        }
        for(Shifts shift:shifts){
            JedisPoolUtils.setPool(5,shift.getShiftsName(),shift.getShiftsCode());
        }
        for(Team team:teams){
            JedisPoolUtils.setPool(6,team.getTeamName(),team.getTeamCode());
        }
        for(TechnologyStandard technologyStandard:technologyStandards){
            JedisPoolUtils.setPool(7,technologyStandard.getStandardName()+technologyStandard.getCigaretteCode(),technologyStandard.getStandardCode());
        }
        for(WorkshopSection workshopSection:workshopSections){
            JedisPoolUtils.setPool(8,workshopSection.getWorkshopSectionName(),workshopSection.getWorkshopSectionCode());
        }*/
    }





}
