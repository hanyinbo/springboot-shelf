package com.aison.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxCompanyOfPageDto implements Serializable {
    private String companyName;
    private String companyCode;
    private String industry;
    private String region;
    private String address;
    private Integer size;
    private Integer current;
}
