package com.aison.entity;

import com.aison.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "m_info_var")
public class MInfoVar extends BaseEntity {
    @TableId
    @TableField(value = "oid")
    private Long oid;
    @TableField(value = "vName")
    private String vName;
    @TableField(value = "r_oid")
    private Long roid;
}
