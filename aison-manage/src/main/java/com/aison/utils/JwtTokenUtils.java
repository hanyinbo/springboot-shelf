package com.aison.utils;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

/**
 * jwt工具类
 *
 * @author aison
 * @version 1.0
 * @date 2019-10-24 11:13
 */
//@ConfigurationProperties 可直接定义yml中属性，变量名可与配置文件属性名成驼峰命名直接使用，切记不可定义static变量
//@ConfigurationProperties(prefix = "jwt")
@Component
@Slf4j
public class JwtTokenUtils {
    /**
     * header名称
     */
    public static String TOKEN_HEADER;
    /**
     * token前缀
     */
    public static String TOKEN_PREFIX;
    /**
     * 秘钥
     */
    public static String SECRET;
    /**
     * 过期时间
     */
    public static String EXPIRATIO;
    /**
     * 选择记住后过期时间
     */
    public static String REMEMBEREXPIRATION;
    /**
     * 权限
     */
    public static String ROLE;

    @Value("${jwt.tokenHeader}")
    public void setTokenHeader(String tokenHeader) {
        JwtTokenUtils.TOKEN_HEADER = tokenHeader;
    }

    @Value("${jwt.tokenPrefix}")
    public void setTokenPrefix(String tokenPrefix) {
        JwtTokenUtils.TOKEN_PREFIX = tokenPrefix + " ";
    }

    @Value("${jwt.secret}")
    public void setSECRET(String SECRET) {
        JwtTokenUtils.SECRET = SECRET;
    }

    @Value("${jwt.expiration}")
    public void setEXPIRATIO(String EXPIRATIO) {
        JwtTokenUtils.EXPIRATIO = EXPIRATIO;
    }

    @Value("${jwt.remembeExpiraton}")
    public void setREMEMBEREXPIRATION(String REMEMBEREXPIRATION) {
        JwtTokenUtils.REMEMBEREXPIRATION = REMEMBEREXPIRATION;
    }

    @Value("${jwt.role}")
    public void setROLE(String ROLE) {
        JwtTokenUtils.ROLE = ROLE;
    }

    /**
     * 生成token
     *
     * @param
     * @return
     */
    public static String createToken(String username, String role, boolean isRememberMe) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put(ROLE, role);
        long expiration = isRememberMe ? Long.valueOf(REMEMBEREXPIRATION) : Long.valueOf(EXPIRATIO);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    /**
     * token规则
     *
     * @param token
     * @return
     */
    public static Claims generateToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取用户名
     *
     * @param token
     * @return
     */
    public static String getUserName(String token) {
        return generateToken(token).getSubject();
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    private static Claims getTokenBody(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return claims;
    }

    //是否已过期
    public static boolean isExpiration(String token) {
        try {
            return getTokenBody(token).getExpiration().before(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
