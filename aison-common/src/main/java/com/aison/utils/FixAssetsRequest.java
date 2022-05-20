package com.aison.utils;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "公共请求参数")
public class FixAssetsRequest implements Serializable {
	private String  appId;
	private String  appSecret;
	private String  sign;
	private String  timestamp;
	private String  param;
	private Integer tenantId;
}
