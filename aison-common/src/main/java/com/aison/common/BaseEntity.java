package com.aison.common;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 共有字段
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -3422432117328693716L;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "creator",fill = FieldFill.INSERT)
    private String creator;
    @TableField(value = "update_time",fill=FieldFill.UPDATE)
    private LocalDateTime updateTime;
    @TableField(value = "updator",fill=FieldFill.UPDATE)
    private String updator;
}
