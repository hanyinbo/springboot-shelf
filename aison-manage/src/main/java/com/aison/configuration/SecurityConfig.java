package com.aison.configuration;

import com.aison.filter.JWTAuthenticationFilter;
import com.aison.filter.JWTAuthorizationFilter;
import com.aison.handler.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Security配置类
 */
@Configuration
@AllArgsConstructor
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)//只有加了@EnableGlobalMethodSecurity(prePostEnabled=true) 那么在上面使用的 @PreAuthorize(“hasAuthority(‘admin’)”)才会生效
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private ObjectMapper objectMapper;

    private ManageAccessDeniedHandler manageAccessDeniedHandler;

    private ManageLogoutSuccessHandler manageLogoutSuccessHandler;

    private ManageAuthenticationEntryPoint manageAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                //未登录，返回JSON
                .authenticationEntryPoint(manageAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
//              .antMatchers("/auth/**","/aison/**")
                //指定某路径不需要权限校验
                .antMatchers("/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated() //必须授权才能访问.
                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin() //使用自带的登录
                .permitAll()
                .and()
                .exceptionHandling()
                //没有权限，返回json
                .accessDeniedHandler(manageAccessDeniedHandler)
                .and()
                .logout()
                //退出成功，返回json
                .logoutSuccessHandler(manageLogoutSuccessHandler)
                .permitAll();
        //开启跨域访问
        http.cors().disable();
        //开启模拟请求，比如API POST测试工具的测试，不开启时，API POST为报403错误
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        //对于在header里面增加token等类似情况，放行所有OPTIONS请求。
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Bean
    public JWTAuthenticationFilter jWTAuthenticationFilter() throws Exception {
        JWTAuthenticationFilter filter = new JWTAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(new ManageAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(new ManageAuthenticationFailureHandler());
        filter.setFilterProcessesUrl("/auth/login");
        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
}
