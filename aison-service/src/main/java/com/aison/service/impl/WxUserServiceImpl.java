package com.aison.service.impl;

import com.aison.entity.WxUser;
import com.aison.mapper.WxUserMapper;
import com.aison.service.WxUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements WxUserService {
}
