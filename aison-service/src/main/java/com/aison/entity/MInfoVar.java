package com.aison.entity;

import com.aison.bean.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "m_info_var")
public class MInfoVar extends BaseEntity {
    @TableId
    @TableField(value = "oid")
    private Long oid;
    @TableField(value = "vName")
    private String vName;
}
