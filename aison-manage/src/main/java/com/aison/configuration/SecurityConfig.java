package com.aison.configuration;

import com.aison.entity.TUser;
import com.aison.filter.JWTAuthenticationTokenFilter;
import com.aison.handler.ManageAccessDeniedHandler;
import com.aison.handler.ManageAuthenticationEntryPoint;
import com.aison.service.TUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//import com.aison.filter.JWTLoginFilter;
//import com.aison.filter.JWTLoginFilter;

/**
 * Security配置类
 */
@Configuration
@AllArgsConstructor
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
// 只有加了@EnableGlobalMethodSecurity(prePostEnabled=true) 那么在上面使用的 @PreAuthorize(“hasAuthority(‘admin’)”)才会生效
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private ManageFilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    private ManageAccessDeniedHandler manageAccessDeniedHandler;

//    private ManageLogoutSuccessHandler manageLogoutSuccessHandler;

    private ManageAuthenticationEntryPoint manageAuthenticationEntryPoint;

//    private ManageAuthenticationSuccessHandler manageAuthenticationSuccessHandler;

//    private ManageAuthenticationFailureHandler manageAuthenticationFailureHandler;
//
//    private JWTAuthenticationFilter jwtAuthenticationFilter;

    private TUserService tUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers("/login", "/logout")
//                .permitAll()
//                除了上面，所有请求都要认证
                .anyRequest()
                .authenticated()
                .and()
                .headers()
                .cacheControl();
//        添加jwt,登录过滤器
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//        添加自定义未授权未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(manageAccessDeniedHandler)
                .authenticationEntryPoint(manageAuthenticationEntryPoint);
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers(
                "/login",
                "/logout",
                "/css/**",
                "/js/**",
                "index.html",
                "favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**"
        );
    }
    @Bean
    public JWTAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JWTAuthenticationTokenFilter();
    }

//    @Bean
//    public JWTLoginFilter jwtLoginFilter() throws Exception {
//        JWTLoginFilter filter = new JWTLoginFilter();
//        filter.setAuthenticationSuccessHandler(manageAuthenticationSuccessHandler);
//        filter.setAuthenticationFailureHandler(manageAuthenticationFailureHandler);
//        filter.setFilterProcessesUrl("/auth/login");
//        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
//        filter.setAuthenticationManager(authenticationManagerBean());
//        return filter;
//
//    }

    @Bean
    public JWTAuthenticationTokenFilter jwtAuthenticationFilter() throws Exception {
        return new JWTAuthenticationTokenFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(getPasswordEncoder());
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            TUser user = tUserService.findUserByUserName(username);
            if (null != user) {
                return user;
            }
            return null;
        };

    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
