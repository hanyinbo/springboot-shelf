package com.aison.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "m_info")//指定表名
public class MInfo {

    @TableField(value = "m_oid")
    @TableId
    private String mOid;
    @TableField(value = "m_name")
    private String mName;
    @TableField(value = "m_type")
    private String mType;
    @TableField(value = "version")
    private Integer version;
    @TableField(value = "remark")
    private String remark;

}
