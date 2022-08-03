package com.aison.service;

import com.aison.entity.TUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * TODO
 *
 * @author hyb
 * @date 2021/9/18 11:00
 */
public interface TUserService  extends IService<TUser> {
     TUser findUserByUserName(String username);

}
