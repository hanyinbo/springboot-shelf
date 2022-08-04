package com.aison.authority;

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

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    public ManageFilterInvocationSecurityMetadataSource(TMenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
//        获取请求的url
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        log.info("获取请求的url:"+requestUrl);
        List<TMenu> menuList = menuService.getMenuWithRole();
        for (TMenu menu : menuList) {
            if(StrUtil.isEmpty(menu.getPath())){
                break;
            }
            if(antPathMatcher.match(menu.getPath(), requestUrl)){
                List<TRole> roles = menu.getRoleList();
                String[] roleArr = new String[roles.size()];
                for (int i = 0; i < roleArr.length; i++){
                    roleArr[i] = roles.get(i).getRoleCode();
                }
                log.info("角色："+roleArr);
                return SecurityConfig.createList(roleArr);
            }
        }
        //如果当前请求的URL在资源表中不存在响应的模式，就假设该请求登录后即可访问，直接返回ROLE_LOGIN
        return SecurityConfig.createList("ROLE_LOGIN");
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
