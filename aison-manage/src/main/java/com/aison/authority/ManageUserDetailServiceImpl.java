package com.aison.authority;

import com.aison.entity.TRole;
import com.aison.entity.TUser;
import com.aison.entity.TUserRole;
import com.aison.service.TRoleService;
import com.aison.service.TUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
@Component("userDetailService")
public class ManageUserDetailServiceImpl implements UserDetailsService {

    private final  TUserService tUserService;
    private final TRoleService tRoleService;

    @Override
    public ManageUserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        ManageUserDetails userInfo = new ManageUserDetails();

        TUser tUser = tUserService.findUserByUserName(loginName);
        if (ObjectUtils.isEmpty(tUser)) {
            throw new UsernameNotFoundException("用户[" + loginName + "]不存在");
        }
        if(tUser.getDelFlag().intValue()==1){
            throw new UsernameNotFoundException("用户[" + loginName + "]不存在");
        }
        userInfo.setId(tUser.getId());
        //登录用户名
        userInfo.setUsername(loginName);
        userInfo.setPassword(tUser.getPassword());
        Set<SimpleGrantedAuthority> authoritiesSet = new HashSet<>();
        List<TRole> userRoles = tRoleService.findRoleByUserId(tUser.getId());
        List<String> roleNames = userRoles.stream().map(TRole::getRoleName).collect(Collectors.toList());
        for (String roleName : roleNames) {
            //用户拥有的角色
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            authoritiesSet.add(simpleGrantedAuthority);
        }
        userInfo.setAuthorities(authoritiesSet);
        return userInfo;
    }
}
