package com.aison.filter;

import com.aison.authority.ManageUserDetails;
import com.aison.service.TUserService;
import com.aison.utils.AccessAddressUtils;
import com.aison.utils.JwtTokenUtils;
import com.aison.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * TODO
 * 鉴定权限
 *
 * @author hyb
 * @date 2021/9/23 10:18
 */
@Component
@Slf4j
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    public JwtTokenUtils jwtTokenUtils;

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        if (token != null && token.startsWith(JwtTokenUtils.TOKEN_PREFIX)) {
            // 是否在黑名单中
            if (JwtTokenUtils.isBlackList(token)) {
                ResponseUtils.responseJson(response, ResponseUtils.response(505, "Token已失效", "Token已进入黑名单"));
                return;
            }
            // 是否存在于Redis中
            if (JwtTokenUtils.hasToken(token)) {
                String ip = AccessAddressUtils.getIpAddress(request);
                String expiration = JwtTokenUtils.getExpirationByToken(token);
                String username = JwtTokenUtils.getUserNameByToken(token);
                // 判断是否过期
                if (JwtTokenUtils.isExpiration(expiration)) {
                    // 加入黑名单
                    JwtTokenUtils.addBlackList(token);
                    // 是否在刷新期内
                    String validTime = JwtTokenUtils.getRefreshTimeByToken(token);
                    if (JwtTokenUtils.isValid(validTime)) {
                        // 刷新Token，重新存入请求头
                        String newToke = JwtTokenUtils.refreshAccessToken(token);
                        // 删除旧的Token，并保存新的Token
                        JwtTokenUtils.deleteRedisToken(token);
                        JwtTokenUtils.setTokenInfo(newToke, username, ip);
                        response.setHeader(JwtTokenUtils.TOKEN_HEADER, newToke);
                        log.info("用户{}的Token已过期，但为超过刷新时间，已刷新", username);
                        token = newToke;
                    } else {
                        log.info("用户{}的Token已过期且超过刷新时间，不予刷新", username);
                        // 加入黑名单
                        JwtTokenUtils.addBlackList(token);
                        ResponseUtils.responseJson(response, ResponseUtils.response(505, "Token已过期", "已超过刷新有效期"));
                        return;
                    }
                }
                ManageUserDetails manageUserDetails = JwtTokenUtils.parseTokenBody(token);
                if (Objects.nonNull(manageUserDetails)) {
                    // 校验IP
                    if (!StringUtils.equals(ip, manageUserDetails.getIp().toString())) {
                        log.info("用户{}请求IP与Token中IP信息不一致", username);
                        // 加入黑名单
                        JwtTokenUtils.addBlackList(token);
                        ResponseUtils.responseJson(response, ResponseUtils.response(505, "Token已过期", "可能存在IP伪造风险"));
                        return;
                    }

                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            manageUserDetails,token, manageUserDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
