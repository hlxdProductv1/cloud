package com.hlxd.microcloud.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class TechnologyStandardVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 指标代码
	 */
	private String standardCode;
	/**
	 * 指标名称
	 */
	private String standardName;
	/**
	 * 所属工艺
	 */
	private String technologyCode;
	/**
	 * 工艺名称
	 */
	private String technologyName;
	/**
	 * 卷烟规格
	 */
	private String cigaretteCode;
	/**
	 * 指标类型 1.定量2.定性
	 */
	private Integer standardType;
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 标准值 private BigDecimal standardValue; /** 最大值
	 */
	private BigDecimal maximum;
	/**
	 * 最小值
	 */
	private BigDecimal minimum;
	/**
	 * 分数
	 */
	private BigDecimal fraction;
}
