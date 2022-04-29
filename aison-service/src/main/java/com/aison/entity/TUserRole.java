package com.aison.entity;

import com.aison.common.BaseEntity;
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
public class TUserRole extends BaseEntity {
    @TableField(value = "role_id")
    @ApiModelProperty(value = "角色表主键")
    private Long roleId;
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户表主键")
    private Long userId;
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "0-正常，1-删除")
    private Integer  delFlag;
}
