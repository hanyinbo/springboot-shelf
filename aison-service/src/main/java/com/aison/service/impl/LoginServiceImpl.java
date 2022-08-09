package com.aison.service.impl;

import com.aison.common.Msg;
import com.aison.common.Result;
import com.aison.entity.TMenu;
import com.aison.entity.TUser;
import com.aison.mapper.LoginMapper;
import com.aison.service.LoginService;
import com.aison.service.TMenuService;
import com.aison.service.TUserService;
import com.aison.utils.JwtTokenUtils;
import com.aison.utils.PasswordAESUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class LoginServiceImpl extends ServiceImpl<LoginMapper, TUser> implements LoginService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TUserService tUserService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Result login(String userName, String password, String code, HttpServletRequest request) {
        // 查询redis中是否有值
        String captchaCode = (String) redisTemplate.opsForValue().get(Constants.KAPTCHA_SESSION_KEY);
        if(StringUtils.isNotBlank(captchaCode)){
            if(captchaCode.equals(code)){
                // 验证有效时间
                Long expire = redisTemplate.boundHashOps(Constants.KAPTCHA_SESSION_KEY).getExpire();
                if (expire < 0L){
                    return Result.build(Msg.CAPTCHA_FAIL,Msg.CAPTCHA_EXPIRE_FAIL);
                }
            }else {
                return Result.build(Msg.CAPTCHA_FAIL,Msg.CAPTCHA_ERROR_FAIL);
            }
        }else {
            return Result.build(Msg.CAPTCHA_FAIL,Msg.CAPTCHA_EXPIRE_ERROR_FAIL);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        try {
            String aesPwd = PasswordAESUtil.decryptedDES(password);
            boolean matches = new BCryptPasswordEncoder().matches(aesPwd, userDetails.getPassword());
            if (userDetails == null || !matches) {
                return Result.build(Msg.LOGIN_FAIL, Msg.LOGIN_USER_PWD_ERROR_FAIL);
            }
            if (!userDetails.isEnabled()) {
                return Result.build(Msg.LOGIN_FAIL, Msg.LOGIN_ACCOUNT_FORBIDDEN_FAIL);
            }
        } catch (Exception e) {
            return Result.build(Msg.LOGIN_FAIL, Msg.LOGIN__ERROR_FAIL);
        }
        //更新security登录对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        String token = jwtTokenUtils.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return Result.buildOk(tokenMap);
    }

    @Override
    public TUser getUserByUserName(String username) {
        return tUserService.findUserByUserName(username);
    }


}
