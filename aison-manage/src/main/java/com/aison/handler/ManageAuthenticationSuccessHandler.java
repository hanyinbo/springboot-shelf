package com.aison.handler;

import com.aison.authority.ManageUserDetails;
import com.aison.utils.AccessAddressUtils;
import com.aison.utils.JwtTokenUtils;
import com.aison.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 * manage登录成功handler处理类
 *
 * @author hyb
 * @date 2021/9/24 14:14
 */
@Component
@Slf4j
public class ManageAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.debug("登录成功");
        ManageUserDetails userDTO = (ManageUserDetails)authentication.getPrincipal();
        // 获得请求IP
        String ip = AccessAddressUtils.getIpAddress(request);
        userDTO.setIp(ip);
        userDTO.setRole("admin");
        userDTO.setRemember(false);
        try{
            String token = jwtTokenUtils.createToken(userDTO);
            // 保存Token信息到Redis中
            JwtTokenUtils.setTokenInfo(token, userDTO.getUsername(), ip);
            // 返回创建成功的token
            // 但是这里创建的token只是单纯的token
            // 按照jwt的规定，最后请求的格式应该是 `Bearer token`
            response.setHeader("token", JwtTokenUtils.TOKEN_PREFIX + token);
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            ResponseUtils.responseJson(response, ResponseUtils.response(200, "登录成功", tokenMap));
        }catch (Exception e){
            ResponseUtils.responseJson(response, ResponseUtils.response(501, "登录失败,Redis服务异常", null));
        }
    }
}
