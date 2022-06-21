package com.aison.service;

import com.aison.common.Result;
import com.aison.dto.LoginParamDto;
import com.aison.entity.TUser;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

public interface LoginService extends IService<TUser> {

    Result login(String userName, String password);

    TUser getUserByUserName(String username);
}
