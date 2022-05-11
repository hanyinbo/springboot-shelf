package com.aison.service.impl;

import com.aison.entity.WxBrokerage;
import com.aison.mapper.WxBrokerageMapper;
import com.aison.service.WxBrokerageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class WxBrokerageServiceImpl extends ServiceImpl<WxBrokerageMapper, WxBrokerage> implements WxBrokerageService {
}
