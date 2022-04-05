package com.aison.entity;

import com.aison.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@TableName(value = "wx_recommend")
@Data
@ApiModel(value="报备表")
public class WxRecommend extends BaseEntity {
    @TableField(value = "id")
    @TableId
    @ApiModelProperty(value = "主键")
    private Long id;
    @TableField(value = "custom_name")
    @ApiModelProperty(value = "客户名称")
    private String customName;
    @TableField(value = "telephone")
    @ApiModelProperty(value = "手机号")
    private String telephone;
    @TableField(value = "gender")
    @ApiModelProperty(value = "性别")
    private String gender;
    @TableField(value = "intention_company")
    @ApiModelProperty(value = "意向公司")
    private String intentionCompany;
    @TableField(value = "company_id")
    @ApiModelProperty(value = "意向公司ID")
    private Integer companyId;
    @TableField(value = "interview_time")
    @ApiModelProperty(value = "面试时间")
    private LocalDateTime interviewTime;
    @TableField(value = "status")
    @ApiModelProperty(value = "客户状态")
    private String status;
    @TableField(value = "recommend_name")
    @ApiModelProperty(value = "推荐人")
    private String recommendName;
    @TableField(value = "recommend_id")
    @ApiModelProperty(value = "推荐人ID")
    private Long recommendId;
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

}
