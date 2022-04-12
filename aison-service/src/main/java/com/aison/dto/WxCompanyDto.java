package com.aison.dto;

import com.aison.entity.WxRecruitInfo;
import lombok.Data;

import java.util.List;

@Data
public class WxCompanyDto {
    private String type;
    private List<WxRecruitInfo> companyList;
}
