package com.aison.entity;

import com.aison.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName(value = "nz_upload_file")
@Data
@ApiModel(value = "文件表", description = "文件表")
public class NzUploadFile  extends BaseEntity {
    @TableField(value = "id")
    @TableId
    @ApiModelProperty(value = "文件表主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    @ApiModelProperty(value = "上传标识")
    private String uid;
    @ApiModelProperty(value = "文件名称")
    private String name;
    @ApiModelProperty(value = "文件名称")
    private String url;
    @ApiModelProperty(value = "文件名称")
    private String status;
}
