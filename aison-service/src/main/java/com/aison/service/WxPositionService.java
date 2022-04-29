package com.aison.service;

import com.aison.dto.WxCompanyOfPageDto;
import com.aison.entity.WxCompany;
import com.aison.entity.WxPosition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface WxPositionService extends IService<WxPosition> {
    IPage<WxPosition> getPageOfPosition(Page page , WxPosition wxPosition);
}
