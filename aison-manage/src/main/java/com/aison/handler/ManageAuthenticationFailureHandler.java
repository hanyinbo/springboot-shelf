package com.aison.handler;

import com.aison.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO
 * manage登录失败handler处理类
 * @author hyb
 * @date 2021/9/24 15:28
 */
@Component
@Slf4j
public class ManageAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.debug("登录失败");
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            ResponseUtils.responseJson(response, ResponseUtils.response(401, "登录失败,用户名或密码错误", e.getMessage()));
        } else if (e instanceof DisabledException) {
            ResponseUtils.responseJson(response, ResponseUtils.response(401, "登录失败,账户被禁用", e.getMessage()));
        } else {
            ResponseUtils.responseJson(response, ResponseUtils.response(401, "登录失败", e.getMessage()));
        }
    }
}
