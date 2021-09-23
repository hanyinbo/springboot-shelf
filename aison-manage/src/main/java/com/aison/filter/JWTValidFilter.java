//package com.aison.filter;
//
//import com.aison.utils.JwtTokenUtils;
//import io.jsonwebtoken.ExpiredJwtException;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//
///**
// * TODO
// *
// * @author hyb
// * @date 2021/9/22 17:22
// */
//
//@Slf4j
//public class JWTValidFilter extends BasicAuthenticationFilter {
//    // 异常处理类
//    @Autowired
//    private HandlerExceptionResolver resolver;
//
//    @Autowired
//    private JwtTokenUtils jwtTokenUtils;
//    /**
//     * SecurityConfig 配置中创建该类实例
//     */
//    public JWTValidFilter(AuthenticationManager authenticationManager, HandlerExceptionResolver resolver) {
//        // 获取授权管理
//        super(authenticationManager);
//        // 获取异常处理类
//        this.resolver = resolver;
//        this.resolver = resolver;
//    }
//
//    public JWTValidFilter(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//    }
//
//    public JWTValidFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
//        super(authenticationManager, authenticationEntryPoint);
//    }
//    /**
//     * 拦截请求
//     *
//     * @param request
//     * @param response
//     * @param chain
//     * @throws IOException
//     * @throws ServletException
//     */
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        log.info("请求方式:{} 请求URL:{} ", request.getMethod(), request.getServletPath());
//        // 获取token, 没有token直接放行
//        String token = request.getHeader("token");
//        if (StringUtils.isBlank(token) || "null".equals(token)) {
//            super.doFilterInternal(request, response, chain);
//            return;
//        }
//        // 有token进行权限验证
//         List<SimpleGrantedAuthority> userAuthList = null;
//        String username = null;
//        try {
//            //  权限列表
//            userAuthList = jwtTokenUtils.getUserAuth(token);
//            //  获取账号
//            username = jwtTokenUtils.getUserName(token);
//        } catch (ExpiredJwtException ex) {
//            resolver.resolveException(request, response, null,new Exception("登录过期"+ex.getMessage()) );
//            return;
//        } catch (Exception e) {
//            resolver.resolveException(request, response, null, new Exception("JWT解析错误"+e.getMessage()));
//            return;
//        }
//        //  添加账户的权限信息，和账号是否为空，然后保存到Security的Authentication授权管理器中
//        if (StringUtils.isNotBlank(username) && userAuthList != null) {
//            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, userAuthList));
//        }
//        super.doFilterInternal(request, response, chain);
//    }
//}
