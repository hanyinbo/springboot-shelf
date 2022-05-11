package com.aison.entity;

import com.aison.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@TableName(value = "wx_brokerage")
@Data
@ApiModel(value="佣金表")
public class WxBrokerage extends BaseEntity {
    @TableField(value = "id")
    @TableId
    @ApiModelProperty(value = "主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    @TableField(value = "cus_name")
    @ApiModelProperty(value = "客户名称")
    private String cusName;
    @TableField(value = "cus_id")
    @ApiModelProperty(value = "客户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long cusId;
    @TableField(value = "company_name")
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    @TableField(value = "company_id")
    @ApiModelProperty(value = "意向公司ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long companyId;
    @TableField(value = "brokerage")
    @ApiModelProperty(value = "总佣金")
    private Integer brokerage;
    @TableField(value = "is_settle")
    @ApiModelProperty(value = "是否结佣")
    private Boolean isSettle;
    @TableField(value = "recomment_name")
    @ApiModelProperty(value = "推荐人")
    private String recommentName;
    @TableField(value = "recomment_id")
    @ApiModelProperty(value = "推荐人ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long recommentId;
    @TableField(value = "interview_time")
    @ApiModelProperty(value = "面试时间")
    private LocalDateTime interviewTime;
    @TableField(value = "induction_time")
    @ApiModelProperty(value = "入职时间")
    private LocalDateTime inductionTime;
    @TableField(value = "leave_office_time")
    @ApiModelProperty(value = "离职时间")
    private LocalDateTime leaveOfficeTime;
    @TableField(value = "status")
    @ApiModelProperty(value = "客户状态 0：待面试 1：已面试 2：已入职 3：已离职")
    private Integer status;
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "删除标识 0：未删除 1：已删除")
    private Integer delFlag;
}
