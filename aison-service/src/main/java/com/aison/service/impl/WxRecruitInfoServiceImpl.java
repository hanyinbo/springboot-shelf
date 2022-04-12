package com.aison.service.impl;

import com.aison.entity.WxRecruitInfo;
import com.aison.mapper.WxRecruitInfoMapper;
import com.aison.service.WxRecruitInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class WxRecruitInfoServiceImpl extends ServiceImpl<WxRecruitInfoMapper, WxRecruitInfo> implements WxRecruitInfoService {
}
