package com.aison.filter;

import com.aison.authority.ManageUserDetailServiceImpl;
import com.aison.authority.ManageUserDetails;
import com.aison.common.Msg;
import com.aison.exception.LoginFailException;
import com.aison.utils.PasswordAESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO
 * 登录验证
 *
 * @author hyb
 * @date 2021/9/22 14:36
 */
@Slf4j
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private ManageUserDetailServiceImpl manageUserDetailService;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ManageUserDetails userInfo = manageUserDetailService.loadUserByUsername(username);
        log.info("ManageAuthenticationProvider监听密码：" + password + "   数据库密码:" + userInfo.getPassword());
        String aesPwd = PasswordAESUtil.decryptedDES(password);
        log.info("前端登录密码解密后：" + aesPwd);
        boolean matches = new BCryptPasswordEncoder().matches(aesPwd, userInfo.getPassword());
        if (!matches) {
            throw new LoginFailException(Msg.TEXT_LOGIN_FAIL);
        }
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
