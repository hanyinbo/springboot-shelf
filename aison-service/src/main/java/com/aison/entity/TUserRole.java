package com.aison.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@TableName(value = "t_user")
@Data
@ApiModel(value="用户角色表",description="系统用户角色表")
public class TUserRole {
    @TableField(value = "role_id")
    @ApiModelProperty(value = "角色表主键")
    private Long roleId;
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户表主键")
    private Long userId;
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
