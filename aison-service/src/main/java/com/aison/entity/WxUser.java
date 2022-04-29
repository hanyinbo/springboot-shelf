package com.aison.entity;

import com.aison.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName(value = "wx_user")
@Data
@ApiModel(value = "小程序用户表", description = "小程序用户表")
public class WxUser extends BaseEntity {
    @TableField(value = "id")
    @TableId
    @ApiModelProperty(value = "用户表主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    @TableField(value = "nick_name")
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @TableField(value = "age")
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @TableField(value = "gender")
    @ApiModelProperty(value = "性别")
    private Integer gender;
    @TableField(value = "language")
    @ApiModelProperty(value = "语言")
    private String language;
    @TableField(value = "city")
    @ApiModelProperty(value = "城市")
    private String city;
    @TableField(value = "province")
    @ApiModelProperty(value = "省份")
    private String province;
    @TableField(value = "country")
    @ApiModelProperty(value = "国家")
    private String country;
    @TableField(value = "avatar_url")
    @ApiModelProperty(value = "头像")
    private String avatarUrl;
    @TableField(value = "phone")
    @ApiModelProperty(value = "手机号")
    private String phone;
    @TableField(value = "address")
    @ApiModelProperty(value = "地址")
    private String address;
    @TableField(value = "active_code")
    @ApiModelProperty(value = "激活码")
    private String activeCode;
    @TableField(value = "identity")
    @ApiModelProperty(value = "身份")
    private String identity;
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "删除标识")
    private Integer delFlag;

}
