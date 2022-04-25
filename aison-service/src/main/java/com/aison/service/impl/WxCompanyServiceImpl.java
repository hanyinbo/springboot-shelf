package com.aison.service.impl;

import com.aison.dto.WxCompanyOfPageDto;
import com.aison.entity.WxCompany;
import com.aison.mapper.WxCompanyMapper;
import com.aison.service.WxCompanyService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class WxCompanyServiceImpl extends ServiceImpl<WxCompanyMapper, WxCompany> implements WxCompanyService {
    @Override
    public IPage<WxCompany> getPageOfCompany(WxCompanyOfPageDto pageDto) {
        Page page = new Page();
        page.setCurrent(pageDto.getCurrent().longValue());
        page.setSize(pageDto.getSize().longValue());
        return baseMapper.getPageOfCompany(page,pageDto);
    }
}
