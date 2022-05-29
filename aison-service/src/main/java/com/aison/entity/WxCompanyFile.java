package com.aison.entity;

import com.aison.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName(value = "wx_company_file")
@Data
@ApiModel(value = "公司文件表", description = "公司文件表")
public class WxCompanyFile extends BaseEntity {
    @TableField(value = "id")
    @TableId
    @ApiModelProperty(value = "文件表主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    @ApiModelProperty(value = "公司ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long companyId;
    @ApiModelProperty(value = "文件ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long fileId;
}
