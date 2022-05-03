package com.aison.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class WxRecruitQueryDto implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String companyName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long companyId;
    private String industry;
    private String region;
    private String address;
    private String interviewAddress;
    private Integer money;
    private Integer number;
    private String welfare;
    private String  jobRequire;
    private LocalDateTime createTime;
}
