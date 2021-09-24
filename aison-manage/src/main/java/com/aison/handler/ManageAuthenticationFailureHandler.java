package com.aison.handler;

import com.alibaba.fastjson.JSON;
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
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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
        PrintWriter out = response.getWriter();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 401);
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            map.put("message", "用户名或密码错误");
        } else if (e instanceof DisabledException) {
            map.put("message", "账户被禁用");
        } else {
            map.put("message", "登录失败!");
        }
        out.write(JSON.toJSONString(map));
        out.flush();
        out.close();
    }
}
