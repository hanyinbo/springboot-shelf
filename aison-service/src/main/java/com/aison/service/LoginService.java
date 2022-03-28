package com.aison.service;

import com.aison.common.Result;
import com.aison.entity.TUser;
import com.baomidou.mybatisplus.extension.service.IService;

public interface LoginService extends IService<TUser> {
    Result login(TUser user);
}
