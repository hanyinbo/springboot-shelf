package com.aison.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@TableName(value = "t_menu")
@Data
@ApiModel(value="菜单表",description="系统菜单表")
public class TMenu {
    @TableField(value = "menu_id")
    @TableId
    @ApiModelProperty(value = "菜单表主键")
    private Long menuId;
    @TableField(value = "menu_name")
    @ApiModelProperty(value = "菜单名称")
    private String menuName;
    @TableField(value = "sort")
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @TableField(value = "type")
    @ApiModelProperty(value = "类型 0：菜单  1：按钮")
    private String type;
    @TableField(value = "parent_id")
    @ApiModelProperty(value = "父项ID")
    private Long parentId;
    @TableField(value = "path")
    @ApiModelProperty(value = "路径")
    private String path;
    @TableField(value = "icon")
    @ApiModelProperty(value = "图标")
    private Long icon;
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "0-正常，1-删除")
    private Integer  delFlag;
    @TableField(value = "creator")
    @ApiModelProperty(value = "创建人")
    private String creator;
    @TableField(value = "creatime")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime creatime;
    @TableField(value = "updator")
    @ApiModelProperty(value = "修改人")
    private String updator;
    @TableField(value = "updatime")
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updatime;

}
