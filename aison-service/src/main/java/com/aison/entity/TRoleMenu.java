package com.aison.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@TableName(value = "t_role_user")
@Data
@ApiModel(value="角色菜单表",description="系统角色菜单表")
public class TRoleMenu {
    @TableField(value = "role_id")
    @TableId
    @ApiModelProperty(value = "角色表主键")
    private Long roleId;
    @TableField(value = "menu_id")
    @ApiModelProperty(value = "角色名称")
    private Long menuId;
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
