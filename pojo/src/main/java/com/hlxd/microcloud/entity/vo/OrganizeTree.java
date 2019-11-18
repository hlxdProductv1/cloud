package com.hlxd.microcloud.entity.vo;

import java.util.List;

import lombok.Data;

@Data
public class OrganizeTree {

	private String id;
	private String title;
	private String parentId;
	private List<OrganizeTree> children;
	private Boolean spread=true;
	
}
