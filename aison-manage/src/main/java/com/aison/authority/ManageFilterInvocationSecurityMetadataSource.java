package com.aison.authority;

import com.aison.entity.TMenu;
import com.aison.service.TMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 资源控制类
 *
 * @author Zhenfeng Li
 * @version 1.0.0
 * @date 2019-11-01 15:13:01
 */
@Component
@Slf4j
public class ManageFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private final TMenuService menuService;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public ManageFilterInvocationSecurityMetadataSource(TMenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        Set<ConfigAttribute> set = new HashSet<>();
        // 获取请求地址
        String requestUrl = ((FilterInvocation) o).getHttpRequest().getServletPath();
        log.debug("请求URI = {} ", requestUrl);
        List<String> menuUrl = menuService.list().stream().map(TMenu::getPath).collect(Collectors.toList());
      // List<String> menuUrl = menuService.findAll().stream().map(Menu::getUrl).collect(Collectors.toList());
        menuUrl.parallelStream().forEach(url ->
        {
            if (antPathMatcher.match(url, requestUrl)) {
                //当前请求需要的权限
                List<String> roleNames = menuService.findAllRoleNameByPath(url);
                roleNames.forEach(roleName -> {
                    SecurityConfig securityConfig = new SecurityConfig(roleName);
                    set.add(securityConfig);
                });
            }
        });
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
