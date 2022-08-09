package com.aison.filter;

import com.aison.common.Msg;
import com.aison.common.Result;
import com.aison.utils.JwtTokenUtils;
import com.alibaba.fastjson.JSONObject;
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
        try {
            //存在token
            if (null != authHeader && authHeader.startsWith(tokenHead)) {
                String authToken = authHeader.substring(tokenHead.length());

                if(!"null".equals(authToken.trim()) && !jwtTokenUtils.checkToken(authToken.trim())){
                    //重置response
                    response.reset();
                    //设置编码格式
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write(JSONObject.toJSONString(Result.build(Msg.TOKEN_FAIL, Msg.TEXT_TOKEN_INVALID_FAIL)));
                    return;
                }
                String username = jwtTokenUtils.getUserNameFromToken(authToken);

                //token存在用户名但未登录
                if (null != username && null == SecurityContextHolder.getContext().getAuthentication()) {
                    //登录
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    //验证token是由有效，重新设置用户对象
                    if (jwtTokenUtils.validateToken(authToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}