package com.aison.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName(value = "m_info_item")//指定表名
public class MInfoItem {
    @TableId
    @TableField(value = "oid")
    private Long oid;
    @TableField(value = "name")
    private String name;
    @TableField(value = "level")
    private Integer level;
    @TableField(value = "r_oid")
    private Long roid;
    @TableField(value = "creatime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creatime;
    @TableField(value = "creator")
    private String creator;
    @TableField(value = "updatime")
    private Date updatime;
    @TableField(value = "updator")
    private String updator;
}
