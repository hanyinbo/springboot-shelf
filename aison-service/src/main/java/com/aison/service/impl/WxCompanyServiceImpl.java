package com.aison.service.impl;

import com.aison.entity.WxCompany;
import com.aison.mapper.WxCompanyMapper;
import com.aison.service.WxCompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class WxCompanyServiceImpl extends ServiceImpl<WxCompanyMapper, WxCompany> implements WxCompanyService {
}
