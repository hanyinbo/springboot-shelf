package com.aison.service.impl;

import com.aison.common.Result;
import com.aison.entity.TUser;
import com.aison.mapper.LoginMapper;
import com.aison.service.LoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginServiceImpl extends ServiceImpl<LoginMapper, TUser> implements LoginService {


    @Override
    public Result login(TUser user) {
        //AuthManager
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
//
//        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//        if(Objects.isNull(authenticate)){
//            throw new RuntimeException("登录失败");
//        }
//        //jwt
//         authenticate.get
        //返回用户
        return null;
    }
}
