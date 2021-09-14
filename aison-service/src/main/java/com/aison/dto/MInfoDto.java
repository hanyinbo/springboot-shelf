package com.aison.dto;

import com.aison.entity.MInfoVar;
import lombok.Data;

import java.util.List;

@Data
public class MInfoDto {
    private Long mOid;
    private String mName;
    private  String mType;
    private Integer version;
    private String remark;
    private List<MInfoItemDto> children;
    private List<MInfoVar> vars;
}
