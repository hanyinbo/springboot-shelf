package com.aison.service.impl;

import com.aison.entity.WxUser;
import com.aison.mapper.WxUserMapper;
import com.aison.service.WxUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements WxUserService {
    @Override
    public IPage<WxUser> getUserOfPage(Page page, WxUser wxUser) {
        return baseMapper.getUserOfPage(page,wxUser);
    }
}
