package com.aison.filter;

import com.aison.authority.ManageUserDetails;
import com.aison.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * TODO
 * 鉴定权限
 * JWTAuthenticationFilter
 *
 * @author hyb
 * @date 2021/9/23 10:18
 */
@Component
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    public JwtTokenUtils jwtTokenUtils;

//    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        System.out.println("jwt123");
        if (StringUtils.isEmpty(token) || !token.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        //有token，校验token
//        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        chain.doFilter(request, response);


//        UsernamePasswordAuthenticationToken authentication = getAuthentication(request, response);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        chain.doFilter(request, response);
//        if (token != null && token.startsWith(JwtTokenUtils.TOKEN_PREFIX)) {
//            // 是否在黑名单中
//            if (JwtTokenUtils.isBlackList(token)) {
//                ResponseUtils.responseJson(response, ResponseUtils.response(505, "Token已失效", "Token已进入黑名单"));
//                return;
//            }
//            // 是否存在于Redis中
//            if (JwtTokenUtils.hasToken(token)) {
//                String ip = AccessAddressUtils.getIpAddress(request);
//                String expiration = JwtTokenUtils.getExpirationByToken(token);
//                String username = JwtTokenUtils.getUserNameByToken(token);
//                // 判断是否过期
//                if (JwtTokenUtils.isExpiration(expiration)) {
//                    // 加入黑名单
//                    JwtTokenUtils.addBlackList(token);
//                    // 是否在刷新期内
//                    String validTime = JwtTokenUtils.getRefreshTimeByToken(token);
//                    if (JwtTokenUtils.isValid(validTime)) {
//                        // 刷新Token，重新存入请求头
//                        String newToke = JwtTokenUtils.refreshAccessToken(token);
//                        // 删除旧的Token，并保存新的Token
//                        JwtTokenUtils.deleteRedisToken(token);
//                        JwtTokenUtils.setTokenInfo(newToke, username, ip);
//                        response.setHeader(JwtTokenUtils.TOKEN_HEADER, newToke);
//                        log.info("用户{}的Token已过期，但为超过刷新时间，已刷新", username);
//                        token = newToke;
//                    } else {
//                        log.info("用户{}的Token已过期且超过刷新时间，不予刷新", username);
//                        // 加入黑名单
//                        JwtTokenUtils.addBlackList(token);
//                        ResponseUtils.responseJson(response, ResponseUtils.response(505, "Token已过期", "已超过刷新有效期"));
//                        return;
//                    }
//                }
//                ManageUserDetails manageUserDetails = JwtTokenUtils.parseTokenBody(token);
//                if (Objects.nonNull(manageUserDetails)) {
//                    // 校验IP
//                    if (!StringUtils.equals(ip, manageUserDetails.getIp())) {
//                        log.info("用户{}请求IP与Token中IP信息不一致", username);
//                        // 加入黑名单
//                        JwtTokenUtils.addBlackList(token);
//                        ResponseUtils.responseJson(response, ResponseUtils.response(505, "Token已过期", "可能存在IP伪造风险"));
//                        return;
//                    }
//                    Authentication authentication = new UsernamePasswordAuthenticationToken(
//                            manageUserDetails,token, manageUserDetails.getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }
//            super.doFilter(request, response,chain);
//        }


//        response.reset();
//        //设置编码格式
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json;charset=UTF-8");
//        response.getWriter().write(JSONObject.toJSONString(430, Integer.parseInt(Msg.TEXT_TOKEN_INVALID_FAIL)));
    }

//    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        try {
//            String token = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
//            if (token != null) {
//                throw new ServiceException("Token不能为空!");
//            }
//            log.info("JWTAuthenticationFilter:"+token);
//            ManageUserDetails manageUserDetails = JwtTokenUtils.parseTokenBody(token);
//            return new UsernamePasswordAuthenticationToken(manageUserDetails, null, manageUserDetails.getAuthorities());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        System.out.println("JWTAuthenticationFilter.getAuthentication");
        String token = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        System.out.println("token:"+token);
        if (token != null) {
            //token格式乱传的会直接报错500，验证为合法的token则会返回用户信息
//            String userAndRole = Jwts.parser()
//                    //设置密钥
//                    .setSigningKey(Constants.SIGNING_KEY)
//                    //替换前缀
//                    .parseClaimsJws(token.replace(Constants.AUTH_HEADER_START_WITH, ""))
//                    .getBody()
//                    .getSubject();
            ManageUserDetails manageUserDetails = JwtTokenUtils.parseTokenBody(token);
            //token校验成功，返回用户名和权限信息，并跳转到controller请求路径
            List<GrantedAuthority> authList = new ArrayList<>();
            Set<? extends GrantedAuthority> authorities = manageUserDetails.getAuthorities();
            authorities.forEach(auth ->{
                authList.add(auth);
            });

//            String user = "";
//            List<GrantedAuthorityImpl> menuCodeList = new ArrayList<>();
//            if (userAndRole != null) {
//                String[] userAndMenus = userAndRole.split(",");
//                int length = userAndMenus.length;
//                if (length > 0) {
//                    user = userAndMenus[0];
//                }
//                for (int i = 1; i < length; i++) {
//                    menuCodeList.add(new GrantedAuthorityImpl(userAndMenus[i]));
//                }
                //最后一个参数是权限，一定要带入，不然方法验证权限不得行！！！可以传emptyList
                return new UsernamePasswordAuthenticationToken(manageUserDetails, null, authList);
            }
            return null;
        }
}