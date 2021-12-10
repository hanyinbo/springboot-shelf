package com.aison.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TMenuRoleVO implements Serializable {
    private static final long serialVersionUID = 4244364696171551139L;

    private Long roleId;
    private Long menuId;
    private String menuName;
    private Long parentId;
    private String path;
    private String icon;
    private Integer sort;
    private String type;
}
