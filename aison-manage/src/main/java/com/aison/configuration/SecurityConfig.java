package com.aison.configuration;

import com.aison.authority.ManageAccessDecisionManager;
import com.aison.authority.ManageAuthenticationProvider;
import com.aison.authority.ManageFilterInvocationSecurityMetadataSource;
import com.aison.filter.JWTAuthenticationFilter;
import com.aison.filter.JWTLoginFilter;
import com.aison.handler.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Security配置类
 */
@Configuration
@AllArgsConstructor
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
// 只有加了@EnableGlobalMethodSecurity(prePostEnabled=true) 那么在上面使用的 @PreAuthorize(“hasAuthority(‘admin’)”)才会生效
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private ManageFilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;
    private ManageAuthenticationProvider mallAuthenticationProvider;

    private ManageAccessDeniedHandler manageAccessDeniedHandler;

    private ManageLogoutSuccessHandler manageLogoutSuccessHandler;

    private ManageAuthenticationEntryPoint manageAuthenticationEntryPoint;

    private ManageAuthenticationSuccessHandler manageAuthenticationSuccessHandler;

    private ManageAuthenticationFailureHandler manageAuthenticationFailureHandler;

//    private JWTAuthenticationFilter jwtAuthenticationTokenFilter;

    private ManageAccessDecisionManager manageAccessDecisionManager;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/sys-file/**").permitAll()
                .anyRequest().authenticated()
//                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//                    @Override
//                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
//                        //权限判断
//                        o.setAccessDecisionManager(manageAccessDecisionManager);
//                        //动态获取url权限配置
//                        o.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
//                        return o;
//                    }
//                })
                .and().httpBasic().authenticationEntryPoint(manageAuthenticationEntryPoint)
                .and().formLogin().permitAll()
                .and().logout().logoutUrl("/logout").logoutSuccessHandler(manageLogoutSuccessHandler).permitAll()
                .and().exceptionHandling().accessDeniedHandler(manageAccessDeniedHandler)
                .and().cors()
                .and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().cacheControl();
        http.addFilter(new JWTAuthenticationFilter(authenticationManager()));

        http.addFilterBefore(jwtLoginFilter(), JWTAuthenticationFilter.class);
//        http.formLogin().loginProcessingUrl("/auth/login").permitAll()
//                .and().logout().logoutUrl("logout")
//                .logoutSuccessHandler(manageLogoutSuccessHandler).permitAll()
//                .and().cors()
//                .and().csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//                .and().authorizeRequests().antMatchers("/swagger-resources", "/swagger-ui.html/**").permitAll()
//                //                //其他全部拦截
//                .and().authorizeRequests().anyRequest().authenticated()
//                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//                    @Override
//                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
//                        //权限判断
//                        o.setAccessDecisionManager(manageAccessDecisionManager);
//                        //动态获取url权限配置
//                        o.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
//                        return o;
//                    }
//                })
//                // 无权访问异常处理
//                .and().exceptionHandling().authenticationEntryPoint(manageAuthenticationEntryPoint)
//                .accessDeniedHandler(manageAccessDeniedHandler);;
//        // 禁用缓存
//        http.headers().cacheControl();
//       // 添加JWT filter
//        //将token验证添加在密码验证前面
////        http.addFilterBefore(getJwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
////        http.addFilterBefore(jwtLoginFilter(), JWTAuthenticationFilter.class);
//        http.addFilter(new JWTAuthenticationFilter(authenticationManager()));


    }

    @Bean
    public JWTLoginFilter jwtLoginFilter() throws Exception {
        JWTLoginFilter filter = new JWTLoginFilter();
        filter.setAuthenticationSuccessHandler(manageAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(manageAuthenticationFailureHandler);
        filter.setFilterProcessesUrl("/auth/login");
        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    /**
     * 用户登录验证
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(mallAuthenticationProvider);
    }
}
