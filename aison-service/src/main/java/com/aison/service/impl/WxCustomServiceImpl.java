package com.aison.service.impl;

import com.aison.entity.WxCustom;
import com.aison.mapper.WxCustomMapper;
import com.aison.service.WxCustomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class WxCustomServiceImpl extends ServiceImpl<WxCustomMapper,WxCustom> implements WxCustomService {
}
