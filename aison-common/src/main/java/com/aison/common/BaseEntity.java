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

    @TableField(value = "creatime",fill = FieldFill.INSERT)
    private LocalDateTime creatime;
    @TableField(value = "creator",fill = FieldFill.INSERT)
    private String creator;
    @TableField(value = "updatime",fill=FieldFill.UPDATE)
    private LocalDateTime updatime;
    @TableField(value = "updator",fill=FieldFill.UPDATE)
    private String updator;
}
