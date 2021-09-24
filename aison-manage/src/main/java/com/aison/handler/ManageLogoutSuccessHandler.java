package com.aison.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
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
 * 登出成功handler处理类
 * @author hyb
 * @date 2021/9/24 16:13
 */
@Component
@Slf4j
public class ManageLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.debug("登出成功");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 200);
        map.put("message", "退出成功");
        map.put("data", authentication);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONString(map));
        out.flush();
        out.close();
    }
}
