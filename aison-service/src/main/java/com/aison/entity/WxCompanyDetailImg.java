package com.aison.entity;

import com.aison.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName(value = "wx_company_detail_img")
@Data
@ApiModel(value = "公司轮播图表", description = "公司轮播图表")
public class WxCompanyDetailImg {
    @TableField(value = "id")
    @TableId
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "图片名称")
    private String imgName;
    @ApiModelProperty(value = "图片地址")
    private String imgUrl;
    @ApiModelProperty(value = "公司ID")
    private Long companyId;
}
