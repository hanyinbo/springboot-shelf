package com.aison.entity;

import com.aison.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName(value = "wx_company")
@Data
@ApiModel(value = "公司表", description = "公司表")
public class WxCompany extends BaseEntity {
    @TableField(value = "id")
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "公司表主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    @ApiModelProperty(value = "公司名称")
    @TableField(value = "company_name")
    private String companyName;
    @ApiModelProperty(value = "公司编码")
    @TableField(value = "company_code")
    private String companyCode;
    @ApiModelProperty(value = "公司简介")
    @TableField(value = "introduce")
    private String introduce;
    @ApiModelProperty(value = "公司地址")
    @TableField(value = "address")
    private String address;
    @ApiModelProperty(value = "所属行业")
    @TableField(value = "industry")
    private String industry;
    @ApiModelProperty(value = "所属区域")
    @TableField(value = "region")
    private String region;
    @ApiModelProperty(value = "删除标识 0:未删除 1：已删除")
    @TableField(value = "del_flag")
    private Integer delFlag;
}
