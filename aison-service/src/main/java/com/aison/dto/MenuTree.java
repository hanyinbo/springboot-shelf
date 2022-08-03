/*
 *
 *      Copyright (c) 2018-2025, fixassets All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: fixassets
 *
 */

package com.aison.dto;

import com.aison.entity.TMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author fixassets
 * @date 2017年11月9日23:33:27
 */
@Data
@ApiModel(value = "菜单树")
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends TreeNode implements Serializable {

	/**
	 * 菜单图标
	 */
	@ApiModelProperty(value = "菜单图标")
	private String icon;

	/**
	 * 菜单名称
	 */
	@ApiModelProperty(value = "菜单名称")
	private String name;

	private boolean spread = false;

	/**
	 * 前端路由标识路径
	 */
	@ApiModelProperty(value = "前端路由标识路径")
	private String path;

	/**
	 * 菜单类型 （0菜单 1按钮）
	 */
	@ApiModelProperty(value = "菜单类型,0:菜单 1:按钮")
	private String type;

	/**
	 * 排序值
	 */
	@ApiModelProperty(value = "排序值")
	private Integer sort;

	/**
	 * 是否包含子节点
	 *
	 * @since 3.7
	 */
	private Boolean hasChildren;

	public MenuTree() {
	}

	public MenuTree(Long id, String name, Long parentId) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
	}

	public MenuTree(Long id, String name, MenuTree parent) {
		this.id = id;
		this.parentId = parent.getId();
		this.name = name;
	}

	public MenuTree(TMenu menuVo) {
		this.id = menuVo.getMenuId();
		this.parentId = menuVo.getParentId();
		this.icon = menuVo.getIcon();
		this.name = menuVo.getMenuName();
		this.path = menuVo.getPath();
		this.type = menuVo.getType();
		this.sort = menuVo.getSort();
	}

}
