package com.aison.filter;

import com.aison.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO
 * 鉴定权限
 * JWTAuthenticationFilter
 *
 * @author hyb
 * @date 2021/9/23 10:18
 */

@Slf4j
public class JWTAuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        String authHeader = request.getHeader(tokenHeader);
//        存在token
        if(null !=authHeader && authHeader.startsWith(tokenHead)){
            String authToken = authHeader.substring(tokenHead.length());
            String username = jwtTokenUtils.getUserNameFromToken(authToken);
//            token存在用户名但未登录
            if(null!=username && null==SecurityContextHolder.getContext().getAuthentication()){
//              登录
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                验证token是由有效，重新设置用户对象
                if(jwtTokenUtils.validateToken(authToken,userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request,response);
//        if (StrUtil.isNotEmpty(token) && token.startsWith(JwtTokenUtils.TOKEN_HEADER)) {
////            chain.doFilter(request, response);
////            return;
//            String authToken = token.substring(JwtTokenUtils.TOKEN_HEADER.length());
//            String username = JwtTokenUtils.getUserNameByToken(authToken);
////          token存在用户名未登录
//            if(null != username &&null==SecurityContextHolder.getContext().getAuthentication()){
////               登录
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                if(JwtTokenUtils.)
//            }
//
//        }
//        log.info("鉴权过滤器执行");
//        log.info("请求的api地址:" + request.getRequestURI());
////        String token = request.getHeader("Bearer ");
//        String s = request.getHeader("Authorization");
//        log.info("token:"+token);
//        log.info("s:"+s);
//        // 巨坑，之前没写这一段，配置类的antMatchers一直失效；如果请求头中没有Authorization信息则直接放行了
//        if (!StringUtils.hasText(s)) {
//            log.warn("请求未携带token，无需校验");
//            chain.doFilter(request, response);
//            return;
//        }
//        String userName = JwtTokenUtils.parseTokenBody(token);
//        UserDetails manageUserDetails = manageUserDetailService.loadUserByUsername(userName);
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
//                (manageUserDetails, null, manageUserDetails.getAuthorities());
//
//        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//        // 认证，注释掉这句就会执行RestfulAuthorizationEntryPoint(401)
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//
//        // 若直接执行这条语句，就会执行自定义未登录的返回结果（RestAuthorizationEntryPoint）
//        chain.doFilter(request, response);
    }
}