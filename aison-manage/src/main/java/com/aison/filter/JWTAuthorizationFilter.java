package com.aison.filter;

import com.aison.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * TODO
 * 鉴定权限
 * @author hyb
 * @date 2021/9/23 10:18
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    public JwtTokenUtils jwtTokenUtils;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String tokenHeader = request.getHeader( jwtTokenUtils.TOKENHEADER);
        // 如果请求头中没有Authorization信息则直接放行了
        if (tokenHeader == null || !tokenHeader.startsWith( jwtTokenUtils.TOKENPREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        //如果请求头中有token,则进行解析，并且设置认证信息
        if(!JwtTokenUtils.isExpiration(tokenHeader.replace(jwtTokenUtils.TOKENPREFIX,""))){
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
        }
        chain.doFilter(request, response);
    }


    // 这里从token中获取用户信息并新建一个token
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        String token = tokenHeader.replace( jwtTokenUtils.TOKENPREFIX, "");
        String username = JwtTokenUtils.getUserName(token);
        if (username != null) {
            return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
        }
        return null;
    }
}
