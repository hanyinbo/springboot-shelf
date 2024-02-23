package com.aison.configuration;


import com.aison.authority.ManageAccessDecisionManager;
import com.aison.authority.ManageFilterInvocationSecurityMetadataSource;
import com.aison.entity.TUser;
import com.aison.filter.JWTAuthenticationTokenFilter;
import com.aison.handler.ManageAccessDeniedHandler;
import com.aison.handler.ManageAuthenticationEntryPoint;
import com.aison.service.TRoleService;
import com.aison.service.TUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private ManageAccessDeniedHandler manageAccessDeniedHandler;

    private ManageAuthenticationEntryPoint manageAuthenticationEntryPoint;

    private TUserService tUserService;

    private TRoleService tRoleService;

    private ManageAccessDecisionManager manageAccessDecisionManager;

    private ManageFilterInvocationSecurityMetadataSource securityMetadataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and().csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
//                动态权限配置
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(manageAccessDecisionManager);
                        o.setSecurityMetadataSource(securityMetadataSource);
                        return o;
                    }
                })
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
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/doLogin",
                "/logout",
                "/css/**",
                "/js/**",
                "index.html",
                "favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/captcha",
                "/eisen"
        );
    }

    @Bean
    public JWTAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JWTAuthenticationTokenFilter();
    }


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
                user.setRoles(tRoleService.findRoleByUserId(user.getId()));
                return user;
            }
            throw new UsernameNotFoundException("用户名或密码不存在");
        };

    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//
//    @Bean
//    public JwtTokenInterceptor authenticationInterceptor() {
//        //拦截器
//        return new JwtTokenInterceptor();
//    }
}
