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
@ApiModel(value="用户表",description="系统用户表")
public class TUser {
    @TableField(value = "id")
    @TableId
    @ApiModelProperty(value = "用户表主键")
    private Long id;
    @TableField(value = "username")
    @ApiModelProperty(value = "用户名")
    private String username;
    @TableField(value = "password")
    @ApiModelProperty(value = "密码")
    private String password;
    @TableField(value = "fullname")
    @ApiModelProperty(value = "全称")
    private String fullname;
    @TableField(value = "mobile")
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "0-正常，1-删除")
    private Boolean  delFlag;
    @TableField(value = "creator")
    @ApiModelProperty(value = "创建人")
    private String creator;
    @TableField(value = "creatime")
    @ApiModelProperty(value = "密码")
    private LocalDateTime creatime;
    @TableField(value = "updator")
    @ApiModelProperty(value = "修改人")
    private String updator;
    @TableField(value = "updatime")
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updatime;
    @TableField(value = "role")
    private String role;
}
