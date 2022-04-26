package com.aison.service;

import com.aison.entity.WxUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface WxUserService extends IService<WxUser> {
    IPage<WxUser> getUserOfPage(Page page , WxUser wxUser);
}
