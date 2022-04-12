package com.aison.service.impl;

import com.aison.entity.WxCompany;
import com.aison.mapper.WxCompanyMapper;
import com.aison.service.WxCompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class WxCompanyServiceImpl extends ServiceImpl<WxCompanyMapper, WxCompany> implements WxCompanyService {
}
