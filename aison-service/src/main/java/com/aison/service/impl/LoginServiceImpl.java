package com.aison.service.impl;

import com.aison.common.Result;
import com.aison.entity.TUser;
import com.aison.mapper.LoginMapper;
import com.aison.service.LoginService;
import com.aison.service.TUserService;
import com.aison.utils.JwtTokenUtils;
import com.aison.utils.PasswordAESUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class LoginServiceImpl extends ServiceImpl<LoginMapper, TUser> implements LoginService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TUserService tUserService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public Result login(String userName, String password, HttpServletRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        try {
            String aesPwd = PasswordAESUtil.decryptedDES(password);
            boolean matches = new BCryptPasswordEncoder().matches(aesPwd, userDetails.getPassword());
            if (userDetails == null || !matches) {
                return Result.build(101, "用户名或密码错误");
            }
            if (!userDetails.isEnabled()) {
                return Result.build(102, "账号被禁用，请联系管理员");
            }
        } catch (Exception e) {
            return Result.build(103, "登录异常，解析密码错误");
        }
        //更新security登录对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        String token = jwtTokenUtils.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return Result.buildOk(tokenMap);
    }

    @Override
    public TUser getUserByUserName(String username) {
        return tUserService.findUserByUserName(username);
    }
}
