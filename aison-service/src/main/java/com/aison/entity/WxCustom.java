package com.aison.entity;

import com.aison.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName(value = "wx_custom")
@Data
@ApiModel(value="客户表")
public class WxCustom extends BaseEntity {
    @TableField(value = "id")
    @TableId
    @ApiModelProperty(value = "主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    @TableField(value = "cus_name")
    @ApiModelProperty(value = "客户名称")
    private String cusName;
    @TableField(value = "gender")
    @ApiModelProperty(value = "性别")
    private Integer gender;
    @TableField(value = "telPhone")
    @ApiModelProperty(value = "手机号")
    private String telPhone;
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "删除标识")
    private Integer delFlag;
}
