package com.aison.authority;

import com.aison.entity.TUser;
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

    private TUserService tUserService;

    @Override
    public ManageUserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        ManageUserDetails userInfo = new ManageUserDetails();

        TUser tUser = tUserService.findUserByUserName(loginName);
        if (ObjectUtils.isEmpty(tUser)) {
            throw new UsernameNotFoundException("用户[" + loginName + "]不存在");
        }
        if(tUser.getDelFlag()){
            throw new UsernameNotFoundException("用户[" + loginName + "]不存在");
        }
        userInfo.setId(tUser.getId());
        //登录用户名
        userInfo.setUsername(loginName);
        userInfo.setPassword(tUser.getPassword());
        Set<SimpleGrantedAuthority> authoritiesSet = new HashSet<>();
//        List<UserRole> userRoles = roleService.findAllByUserId(user.getId());
//        List<String> roleNames = userRoles.stream().map(UserRole::getRole).collect(Collectors.toList()).stream().map(Role::getRoleName).collect(Collectors.toList());
          List<String> roleNames =  Arrays.asList("admin");
        for (String roleName : roleNames) {
            //用户拥有的角色
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            authoritiesSet.add(simpleGrantedAuthority);
        }
        userInfo.setAuthorities(authoritiesSet);

        return userInfo;
    }
}
