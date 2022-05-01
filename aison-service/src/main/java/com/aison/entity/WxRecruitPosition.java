package com.aison.entity;

import com.aison.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName(value = "wx_recruit_position")
@Data
@ApiModel(value="岗位表")
public class WxRecruitPosition extends BaseEntity {
    @TableField(value = "id")
    @TableId
    @ApiModelProperty(value = "主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    @ApiModelProperty(value = "岗位名称")
    private String positionName;
    @ApiModelProperty(value = "岗位说明")
    private String remark;
    @ApiModelProperty(value = "招聘ID")
    private Long recruitId;
}
