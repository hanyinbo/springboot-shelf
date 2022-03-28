package com.aison.configuration;

import com.aison.authority.ManageAccessDecisionManager;
import com.aison.authority.ManageFilterInvocationSecurityMetadataSource;
import com.aison.filter.JWTAuthenticationFilter;
import com.aison.filter.JWTLoginFilter;
import com.aison.handler.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
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

    private ManageFilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    private ManageAccessDeniedHandler manageAccessDeniedHandler;

    private ManageLogoutSuccessHandler manageLogoutSuccessHandler;

    private ManageAuthenticationEntryPoint manageAuthenticationEntryPoint;

    private ManageAuthenticationSuccessHandler manageAuthenticationSuccessHandler;

    private ManageAuthenticationFailureHandler manageAuthenticationFailureHandler;

    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/sys-file/**", "/mini/**").permitAll()
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
                .and().formLogin().permitAll()
                .and().logout().logoutUrl("/logout").logoutSuccessHandler(manageLogoutSuccessHandler).permitAll()
                .and().cors()
                .and().csrf().disable();
        http.exceptionHandling().accessDeniedHandler(manageAccessDeniedHandler).authenticationEntryPoint(manageAuthenticationEntryPoint);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilterAt(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtLoginFilter(),  UsernamePasswordAuthenticationFilter.class);
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

//    @Bean
//    public JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception {
//        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager());
//        return jwtAuthenticationFilter;
//    }
}
