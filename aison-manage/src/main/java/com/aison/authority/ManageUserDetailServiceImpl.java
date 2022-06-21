package com.aison.authority;

import cn.hutool.core.util.StrUtil;
import com.aison.entity.TRole;
import com.aison.entity.TUser;
import com.aison.service.TRoleService;
import com.aison.service.TUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO
 * security 自定义登陆逻辑类
 * 用来做登陆认证，验证用户名与密码
 *
 * @author hyb
 * @date 2021/9/18 11:21
 */
@AllArgsConstructor
@Component
public class ManageUserDetailServiceImpl implements UserDetailsService {

    private final  TUserService tUserService;

    private final TRoleService tRoleService;

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        if(StrUtil.isEmpty(loginName)){
            throw new RuntimeException("用户名不能为空");
        }
        TUser userInfo = new TUser();

        TUser tUser = tUserService.findUserByUserName(loginName);
        if (tUser == null) {
            throw new UsernameNotFoundException(String.format("%s这个用户不存在",loginName));
        }
        userInfo.setId(tUser.getId());
        //登录用户名
        userInfo.setUsername(loginName);
        userInfo.setPassword(tUser.getPassword());
        Set<GrantedAuthority> authorities = new HashSet<>();
        List<TRole> userRoles = tRoleService.findRoleByUserId(tUser.getId());
        List<String> roleCodeList = userRoles.stream().map(TRole::getRoleCode).collect(Collectors.toList());
        for (String roleCode : roleCodeList) {
            //用户拥有的角色
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleCode);
            authorities.add(simpleGrantedAuthority);
        }
//        userInfo.setAuthorities(authorities);
        return new User(userInfo.getUsername(),userInfo.getPassword(),authorities);
    }
}
