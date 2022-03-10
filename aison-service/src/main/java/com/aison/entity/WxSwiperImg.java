package com.aison.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@TableName(value = "wx_swiper_img")
@Data
@ApiModel(value = "轮播图表", description = "轮播图表")
public class WxSwiperImg {
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
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "创建人")
    private String creator;
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;
    @ApiModelProperty(value = "修改人")
    private String updator;

}
