package com.aison.dto;

import com.aison.entity.WxCompany;
import lombok.Data;

import java.util.List;

@Data
public class WxCompanyDto {
    private String type;
    private List<WxCompany> companyList;
}
