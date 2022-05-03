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

import java.util.List;

/**
 * 公司招聘信息表
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName(value = "wx_recruit_info")
@Data
@ApiModel(value = "招聘详情表", description = "招聘详情表")
public class WxRecruitInfo extends BaseEntity {
    @TableField(value = "id")
    @TableId
    @ApiModelProperty(value = "招聘详情表主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    @ApiModelProperty(value = "公司ID")
    private Long companyId;
    @ApiModelProperty(value = "面试地址")
    private String interviewAddress;
    @ApiModelProperty(value = "招聘岗位")
    private String position;
    @TableField(exist = false)
    @ApiModelProperty(value = "招聘岗位")
    private List<String> wxPositionList;
    @ApiModelProperty(value = "佣金")
    private Integer money;
    @ApiModelProperty(value = "招聘人数")
    private Integer number;
    @ApiModelProperty(value = "福利待遇")
    private String welfare;
    @ApiModelProperty(value = "招聘要求")
    private String jobRequire;
    @ApiModelProperty(value = "公司图片URL")
    private String companyImgUrl;

    @TableField(exist = false)
    @ApiModelProperty(value = "公司轮播图")
    private List<WxCompanyDetailImg> companyDetailImgs;
    @TableField(exist = false)
    @ApiModelProperty(value = "招聘岗位")
    private List<WxRecruitPosition> wxRecruitPositionList;
}
