//package com.aison.handler;
//
//import com.aison.utils.JwtTokenUtils;
//import com.aison.utils.ResponseUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * TODO
// * 登出成功handler处理类
// * @author hyb
// * @date 2021/9/24 16:13
// */
//@Component
//@Slf4j
//public class ManageLogoutSuccessHandler implements LogoutSuccessHandler {
//    @Override
//    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        // 添加到黑名单
//        String token = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
//        JwtTokenUtils.addBlackList(token);
//        log.info("用户{}登出成功，Token信息已保存到Redis的黑名单中", JwtTokenUtils.getUserNameByToken(token));
//        SecurityContextHolder.clearContext();
//        ResponseUtils.responseJson(response, ResponseUtils.response(200, "登出成功", null));
//    }
//}
