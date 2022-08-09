package com.aison.utils;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * jwt工具类
 *
 * @author aison
 * @version 1.0
 * @date 2019-10-24 11:13
 * <p>
 * * @ConfigurationProperties 可直接定义yml中属性，变量名可与配置文件属性名成驼峰命名直接使用，切记不可定义static变量
 * @ConfigurationProperties(prefix = "jwt")
 */

@Component
@Slf4j
public class JwtTokenUtils {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";


    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;


    /**
     * 根据用户信息获取token
     *
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 根据token获取用户名
     *
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFormToken(token);
            if (claims == null) {
                return null;
            }
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;

    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        if (StrUtil.isEmpty(username)) {
            return false;
        }
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否存在与有效
     *
     * @param token
     * @return
     */
    public boolean checkToken(String token) {
        try {
            if (StrUtil.isEmpty(token)) return false;
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            if (claims.getExpiration().before(new Date())) return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 判断token是否失效
     *
     * @param token
     * @return
     */
    public Boolean isTokenExpired(String token) {
        Date expireDate = getExpireDateFormToken(token);
        return expireDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     *
     * @param token
     * @return
     */
    private Date getExpireDateFormToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return claims.getExpiration();
    }

    /**
     * 判断token是否过期
     *
     * @param token
     * @return
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     *
     * @param
     * @return
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFormToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    private Claims getClaimsFormToken(String token) {
        Claims claims = null;
        try {
            if (StrUtil.isEmpty(token)) {
                return null;
            }
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
        }
        return claims;
    }

    /**
     * 根据荷载生成TOKEN
     *
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 生成token生效时间
     *
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }
}
