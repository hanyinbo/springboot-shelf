package com.aison.entity;

import com.aison.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value = "wx_swiper_img")
@Data
@ApiModel(value = "轮播图表", description = "轮播图表")
public class WxSwiperImg extends BaseEntity {
    @TableField(value = "id")
    @TableId
    @ApiModelProperty(value = "用户表主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    @ApiModelProperty(value = "图片名称")
    private String imgName;
    @ApiModelProperty(value = "图片地址")
    private String imgUrl;
    @ApiModelProperty(value = "图片导航地址")
    private String navigatorUrl;
    @ApiModelProperty(value = "图片类型 0：轮播图 1：导航栏 ")
    private String imgType;
}
