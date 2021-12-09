package com.aison.configuration;

import com.aison.authority.ManageAccessDecisionManager;
import com.aison.authority.ManageAuthenticationProvider;
import com.aison.filter.JWTAuthenticationFilter;
import com.aison.filter.JWTAuthorizationFilter;
import com.aison.handler.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//import com.aison.authority.ManageAuthenticationProvider;

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
    JWTAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 无权访问异常处理
        http.exceptionHandling().authenticationEntryPoint(manageAuthenticationEntryPoint).accessDeniedHandler(manageAccessDeniedHandler);
        http.formLogin()
                .loginProcessingUrl("/auth/login")
                .loginPage("/auth/login")
                .successHandler(manageAuthenticationSuccessHandler).failureHandler(manageAuthenticationFailureHandler).permitAll()
                .and().logout().logoutUrl("logout")
                .logoutSuccessHandler(manageLogoutSuccessHandler).permitAll()
                .and().cors()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers( "/swagger-resources", "/swagger-ui.html/**").permitAll()
//                //其他全部拦截
                .anyRequest().authenticated();
        // 禁用缓存
        http.headers().cacheControl();
        //添加登录 filter
//        http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
//        // 添加JWT filter
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
        @Override
    public void configure(AuthenticationManagerBuilder auth) {
        //自定义登录认证
        auth.authenticationProvider(mallAuthenticationProvider);
    }
//    @Bean
//    public JWTAuthenticationFilter jWTAuthenticationFilter() throws Exception {
//        JWTAuthenticationFilter filter = new JWTAuthenticationFilter();
//        filter.setAuthenticationSuccessHandler(manageAuthenticationSuccessHandler);
//        filter.setAuthenticationFailureHandler(manageAuthenticationFailureHandler);
//        filter.setFilterProcessesUrl("/auth/login");
//        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
//       filter.setAuthenticationManager(authenticationManagerBean());
//        return filter;
//    }
}
