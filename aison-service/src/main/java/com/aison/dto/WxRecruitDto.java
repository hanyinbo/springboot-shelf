package com.aison.dto;

import com.aison.entity.WxCompany;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class WxRecruitDto implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private WxCompany wxCompany;
    private String companyName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long companyId;
    private List<String> wxPositionList;
    private String interviewAddress;
    private Integer money;
    private Integer number;
    private String welfare;
    private String jobRequire;
}
