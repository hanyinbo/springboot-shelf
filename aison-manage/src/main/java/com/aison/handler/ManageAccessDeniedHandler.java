package com.aison.handler;

import com.aison.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO
 *  权限不足handler处理类
 * @author hyb
 * @date 2021/9/24 16:04
 */
@Component
@Slf4j
public class ManageAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        log.info("没有权限");
//        response.reset();
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ResponseUtils.responseJson(response, ResponseUtils.response(407, "权限不足", e.getMessage()));
    }
}
