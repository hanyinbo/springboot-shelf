package com.aison.service;

import com.aison.dto.WxRecruitQueryDto;
import com.aison.entity.WxPosition;
import com.aison.entity.WxRecruitInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface WxRecruitInfoService extends IService<WxRecruitInfo> {

    IPage<WxRecruitQueryDto> getRecruitOfPage(Page page ,WxRecruitQueryDto dto);
}
