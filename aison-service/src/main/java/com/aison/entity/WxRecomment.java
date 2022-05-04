package com.aison.entity;

import com.aison.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.time.LocalDateTime;

@TableName(value = "wx_recomment")
@Data
@ApiModel(value="报备表")
public class WxRecomment extends BaseEntity {
    @TableField(value = "id")
    @TableId
    @ApiModelProperty(value = "主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    @TableField(value = "custom_name")
    @ApiModelProperty(value = "客户名称")
    private String customName;
    @TableField(value = "telephone")
    @ApiModelProperty(value = "手机号")
    private String telephone;
    @TableField(value = "gender")
    @ApiModelProperty(value = "性别 0：男 1：女")
    private Integer gender;
    @TableField(value = "intention_company")
    @ApiModelProperty(value = "意向公司")
    private String intentionCompany;
    @TableField(value = "company_id")
    @ApiModelProperty(value = "意向公司ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long companyId;
    @TableField(value = "interview_time")
    @ApiModelProperty(value = "面试时间")
    private LocalDateTime interviewTime;
    @TableField(value = "status")
    @ApiModelProperty(value = "客户状态 0：待面试 1：已面试 2：已入职 3：已离职")
    private Integer status;
    @TableField(value = "recomment_name")
    @ApiModelProperty(value = "推荐人")
    private String recommentName;
    @TableField(value = "recomment_id")
    @ApiModelProperty(value = "推荐人ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long recommentId;
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

}
