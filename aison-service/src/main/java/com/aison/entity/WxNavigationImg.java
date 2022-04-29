package com.aison.entity;

import com.aison.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName(value = "wx_navigation_img")
@Data
@ApiModel(value = "导航图表", description = "导航图表")
public class WxNavigationImg extends BaseEntity {
    @TableField(value = "id")
    @TableId
    @ApiModelProperty(value = "用户表主键")
    private Long id;
    @ApiModelProperty(value = "图片名称")
    private String imgName;
    @ApiModelProperty(value = "图片地址")
    private String imgUrl;
    @ApiModelProperty(value = "图片导航地址")
    private String navigatorUrl;
}
