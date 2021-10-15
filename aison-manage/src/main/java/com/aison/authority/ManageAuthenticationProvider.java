package com.aison.authority;

import com.aison.common.Msg;
import com.aison.exception.LoginFailException;
import com.aison.utils.PasswordAESUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * TODO
 * 登录验证类
 * @author hyb
 * @date 2021/9/18 15:19
 */
@AllArgsConstructor
@Component
@Slf4j
public class ManageAuthenticationProvider implements AuthenticationProvider {

    private ManageUserDetailServiceImpl manageUserDetailService;

    private PasswordAESUtil passwordAESUtil;
    @Override
    public  Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //表单输入的用户名
        String loginName = (String) authentication.getPrincipal();
        //表单输入的密码
        String password = (String) authentication.getCredentials();
        ManageUserDetails userInfo = manageUserDetailService.loadUserByUsername(loginName);
        log.info("ManageAuthenticationProvider监听密码："+password +"   数据库密码:"+userInfo.getPassword());
        String aesPwd = PasswordAESUtil.decryptedDES(password);
        log.info("前端登录密码解密后："+aesPwd);
        boolean matches = new BCryptPasswordEncoder().matches(aesPwd, userInfo.getPassword());
        if (!matches) {
            throw new LoginFailException(Msg.TEXT_LOGIN_FAIL);
        }
        return new UsernamePasswordAuthenticationToken(userInfo, userInfo.getPassword(), userInfo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    public static void main(String[] args) {

        String password = new BCryptPasswordEncoder().encode("123");
        log.info("密码长度："+password.length());
        log.info("加密密码"+password);
    }
}
