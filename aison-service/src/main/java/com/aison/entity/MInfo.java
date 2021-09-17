package com.aison.entity;

import com.aison.bean.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName(value = "m_info")//指定表名
@ApiModel(value="MInfo对象",description="测试主表MInfo")
public class MInfo extends BaseEntity {

    @TableField(value = "m_oid")
    @TableId
    @ApiModelProperty(value = "moid主键")
    private Long mOid;
    @TableField(value = "m_name")
    @ApiModelProperty(value = "mName")
    private String mName;
    @ApiModelProperty(value = "mType")
    @TableField(value = "m_type")
    private String mType;
    @ApiModelProperty(value = "version")
    @TableField(value = "version")
    private Integer version;
    @ApiModelProperty(value = "remark")
    @TableField(value = "remark")
    private String remark;


}
