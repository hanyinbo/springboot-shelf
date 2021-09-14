package com.aison.entity;

import com.aison.bean.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "m_info_item")//指定表名
public class MInfoItem extends BaseEntity {
    @TableId
    @TableField(value = "oid")
    private Long oid;
    @TableField(value = "name")
    private String name;
    @TableField(value = "level")
    private Integer level;
    @TableField(value = "r_oid")
    private Long roid;
}
