package com.aison.entity;

import com.aison.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WxPosition extends BaseEntity {
    @TableId
    @TableField(value = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    @TableField(value = "position_name")
    private String positionName;
    @TableField(value = "position_code")
    private String positionCode;
    @TableField(value = "del_flag")
    private Integer delFlag;
}

