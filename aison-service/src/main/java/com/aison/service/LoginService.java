package com.aison.service;

import com.aison.common.Result;
import com.aison.entity.TMenu;
import com.aison.entity.TUser;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface LoginService extends IService<TUser> {

    Result login(String userName, String password, String code, HttpServletRequest request);

    TUser getUserByUserName(String username);

    List<TMenu> getMenuListByUserId();
}
