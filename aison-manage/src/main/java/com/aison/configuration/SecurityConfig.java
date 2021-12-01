package com.aison.configuration;

import com.aison.authority.ManageAccessDecisionManager;
import com.aison.authority.ManageAuthenticationProvider;
import com.aison.filter.JWTAuthenticationFilter;
import com.aison.filter.JWTAuthorizationFilter;
import com.aison.handler.*;
import com.aison.service.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security配置类
 */
@Configuration
@AllArgsConstructor
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
// 只有加了@EnableGlobalMethodSecurity(prePostEnabled=true) 那么在上面使用的 @PreAuthorize(“hasAuthority(‘admin’)”)才会生效
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private ManageAuthenticationProvider mallAuthenticationProvider;

    private ManageAccessDeniedHandler manageAccessDeniedHandler;

    private ManageLogoutSuccessHandler manageLogoutSuccessHandler;

    private ManageAuthenticationEntryPoint manageAuthenticationEntryPoint;

    private ManageAuthenticationSuccessHandler manageAuthenticationSuccessHandler;

    private ManageAuthenticationFailureHandler manageAuthenticationFailureHandler;

    private JWTAuthorizationFilter jwtAuthenticationTokenFilter;

    private ManageAccessDecisionManager manageAccessDecisionManager;

    private RedisService redisService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .permitAll()
                .and().logout()
                .logoutUrl("logout")
                .logoutSuccessHandler(manageLogoutSuccessHandler)
                .permitAll()
                .and().cors()
        .and()
                .csrf()
                .disable()
        .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
                .authorizeRequests()
                .antMatchers( "/swagger-resources", "/swagger-ui.html/**")
                .permitAll()
                //其他全部拦截
                .anyRequest().authenticated()
        .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                //动态获取url权限配置
//                o.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
                //权限判断
                o.setAccessDecisionManager(manageAccessDecisionManager);
                return o;
            }
        })
                //无权访问异常处理器
                .and().exceptionHandling().accessDeniedHandler(manageAccessDeniedHandler)
                //用户未登录处理
                .authenticationEntryPoint(manageAuthenticationEntryPoint);
        // 禁用缓存
        http.headers().cacheControl();
        //添加登录 filter
        http.addFilterBefore(jWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        // 添加JWT filter
        http.addFilterAt(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        //自定义登录认证
        auth.authenticationProvider(mallAuthenticationProvider);
    }

    @Bean
    public JWTAuthenticationFilter jWTAuthenticationFilter() throws Exception {
        JWTAuthenticationFilter filter = new JWTAuthenticationFilter(redisService);
        filter.setAuthenticationSuccessHandler(manageAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(manageAuthenticationFailureHandler);
        filter.setFilterProcessesUrl("/auth/login");
        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
}
