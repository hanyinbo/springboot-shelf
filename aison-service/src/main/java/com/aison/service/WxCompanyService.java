package com.aison.service;

import com.aison.dto.WxCompanyOfPageDto;
import com.aison.entity.WxCompany;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface WxCompanyService extends IService<WxCompany> {

   IPage<WxCompany> getPageOfCompany(WxCompanyOfPageDto pageDto);
}
