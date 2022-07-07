package com.aison.controller;

import com.aison.common.Result;
import com.aison.dto.LoginParamDto;
import com.aison.entity.TUser;
import com.aison.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Api(value = "登录模块")
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public Result login(@RequestBody LoginParamDto loginParamDto){
        return loginService.login(loginParamDto.getUsername(),loginParamDto.getPassword());
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/getUserInfo")
    public Result getUserInfo(Principal principal){
        if(null==principal){
            return Result.buildOk(null);
        }
        String username = principal.getName();
        TUser tUser = loginService.getUserByUserName(username);
        tUser.setPassword(null);
        return Result.buildOk(tUser);
    }
}
