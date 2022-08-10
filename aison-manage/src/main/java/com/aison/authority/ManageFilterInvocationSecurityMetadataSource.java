package com.aison.authority;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.aison.dto.MenuTree;
import com.aison.entity.TMenu;
import com.aison.entity.TRole;
import com.aison.service.TMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 资源控制类
 *
 * @author hyb
 * 　* @date 2022/2/28 14:07
 */
@Component
@Slf4j
public class ManageFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private TMenuService menuService;

    public ManageFilterInvocationSecurityMetadataSource(TMenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        Set<ConfigAttribute> set = new HashSet<>();
        //获取请求的url
        List<TMenu> menuList = menuService.getMenuWithRole();
        for (TMenu menu : menuList) {
            List<String> rolesCodeList = menu.getRoleList().stream().map(TRole::getRoleCode).collect(Collectors.toList());
            rolesCodeList.forEach(roleCode -> {
                SecurityConfig securityConfig = new SecurityConfig(roleCode);
                set.add(securityConfig);
            });
        }

        //如果当前请求的URL在资源表中不存在响应的模式，就假设该请求登录后即可访问，直接返回ROLE_LOGIN
        if (CollUtil.isEmpty(set)) {
            SecurityConfig securityConfig = new SecurityConfig("ROLE_LOGIN");
            set.add(securityConfig);
        }
        return set;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
