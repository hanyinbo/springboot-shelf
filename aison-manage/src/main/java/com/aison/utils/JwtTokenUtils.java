package com.aison.utils;

import com.aison.authority.ManageUserDetailServiceImpl;
import com.aison.authority.ManageUserDetails;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
     * 时间格式化
     */
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ManageUserDetailServiceImpl manageUserDetailService;

    private static JwtTokenUtils jwtTokenUtils;

    @PostConstruct
    public void init() {
        jwtTokenUtils = this;
        jwtTokenUtils.manageUserDetailService = this.manageUserDetailService;
    }

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
    public static Integer EXPIRATIO;
    /**
     * 选择记住后过期时间
     */
    public static Integer REMEMBEREXPIRATION;

    /**
     * 有效时间
     */
    public static Integer REFRESH_EXPIRATION;

//    /**
//     * 权限
//     */
//    public static String ROLE;

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
    public void setEXPIRATIO(Integer EXPIRATIO) {
        JwtTokenUtils.EXPIRATIO = EXPIRATIO * 1000;
    }

    @Value("${jwt.remembeExpiraton}")
    public void setREMEMBEREXPIRATION(Integer REMEMBEREXPIRATION) {
        JwtTokenUtils.REMEMBEREXPIRATION = REMEMBEREXPIRATION;
    }

    @Value("${jwt.refreshExpiraton}")
    public void setRefreshExpiration(Integer refreshExpiraton) {
        JwtTokenUtils.REFRESH_EXPIRATION = refreshExpiraton * 24 * 60 * 60 * 1000;
    }

    /**
     * 生成token
     *
     * @param
     * @return
     */
    public static String createToken(ManageUserDetails userDetails) {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        StringBuffer stringBuffer = new StringBuffer();
        for (GrantedAuthority authority : authorities) {
            stringBuffer.append(authority.getAuthority()).append(",");
        }
        log.info("过期时长:"+REMEMBEREXPIRATION);
        return Jwts.builder()
                .setId(userDetails.getId().toString()) //用户ID
                .signWith(SignatureAlgorithm.HS512, SECRET) //签名算法、密钥
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date()) //签发时间
                .setExpiration(new Date(System.currentTimeMillis() + REMEMBEREXPIRATION))//过期时间
                .claim("authorities", stringBuffer)// 自定义其他属性，如用户组织机构ID，用户所拥有的角色，用户权限信息等
                .claim("ip", userDetails.getIp())
                .compact();
    }

    /**
     * 保存Token信息到Redis中
     *
     * @param token    Token信息
     * @param username 用户名
     * @param ip       IP
     */
    public static void setTokenInfo(String token, String username, String ip) {
        if (StringUtils.isNotEmpty(token)) {
            // 去除JWT前缀
//          token = token.substring(JwtTokenUtils.TOKEN_PREFIX.length());
            Integer refreshTime = JwtTokenUtils.REFRESH_EXPIRATION;
            LocalDateTime localDateTime = LocalDateTime.now();
            log.info("保存token到redis:" + username + "---username----refreshTime:---" + refreshTime);
            RedisUtils.hset(token, "username", username, refreshTime);
            RedisUtils.hset(token, "ip", ip, refreshTime);
            RedisUtils.hset(token, "refreshTime",
                    df.format(localDateTime.plus(JwtTokenUtils.REFRESH_EXPIRATION, ChronoUnit.MILLIS)), refreshTime);
            RedisUtils.hset(token, "expiration", df.format(localDateTime.plus(JwtTokenUtils.EXPIRATIO, ChronoUnit.MILLIS)),
                    refreshTime);
        }
    }

    /**
     * 刷新Token
     *
     * @param oldToken 过期但未超过刷新时间的Token
     * @return
     */
    public static String refreshAccessToken(String oldToken) {
        String username = JwtTokenUtils.getUserNameByToken(oldToken);
        ManageUserDetails userInfo = (ManageUserDetails) jwtTokenUtils.manageUserDetailService.loadUserByUsername(username);
        //userInfo.setIp(JwtTokenUtils.getIpByToken(oldToken));
        return createToken(userInfo);
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public static ManageUserDetails parseTokenBody(String token) {
        ManageUserDetails detailsDTO = null;
        try {
            log.info("解析token:"+token);
            // 去除JWT前缀
            token = token.substring(JwtTokenUtils.TOKEN_PREFIX.length());

            Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
            detailsDTO = new ManageUserDetails();
            log.info("Long.parseLong(claims.getId())----"+Long.parseLong(claims.getId()));
            detailsDTO.setId(Long.parseLong(claims.getId()));
            detailsDTO.setUsername(claims.getSubject());
            // 获取角色
            Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
            String authority = claims.get("authorities").toString();
            if (StringUtils.isNotEmpty(authority)) {
                List<Map<String, String>> authorityList = JSON.parseObject(authority,
                        new TypeReference<List<Map<String, String>>>() {
                        });
                for (Map<String, String> role : authorityList) {
                    if (!role.isEmpty()) {
                        authorities.add(new SimpleGrantedAuthority(role.get("authority")));
                    }
                }
            }
            detailsDTO.setAuthorities(authorities);
            //detailsDTO.setIp(claims.get("ip").toString());
        } catch (Exception e) {
            log.error("解析token失败");
            e.printStackTrace();
        }
        return detailsDTO;
    }

    /**
     * 是否过期
     *
     * @param expiration 过期时间，字符串
     * @return 过期返回True，未过期返回false
     */
    public static boolean isExpiration(String expiration) {
        LocalDateTime expirationTime = LocalDateTime.parse(expiration, df);
        LocalDateTime localDateTime = LocalDateTime.now();
        if (localDateTime.compareTo(expirationTime) > 0) {
            return true;
        }
        return false;
    }
    //是否已过期
//    public static boolean isExpiration(String token) {
//        try {
//            return parseTokenBody(token).get().before(new Date());
//        } catch (Exception e) {
//            log.info("校验报错");
//            e.printStackTrace();
//        }
//        return true;
//    }

    /**
     * 是否有效
     *
     * @param refreshTime 刷新时间，字符串
     * @return 有效返回True，无效返回false
     */
    public static boolean isValid(String refreshTime) {
        LocalDateTime validTime = LocalDateTime.parse(refreshTime, df);
        LocalDateTime localDateTime = LocalDateTime.now();
        if (localDateTime.compareTo(validTime) > 0) {
            return false;
        }
        return true;
    }

    /**
     * 检查Redis中是否存在Token
     *
     * @param token Token信息
     * @return
     */
    public static boolean hasToken(String token) {
        if (StringUtils.isNotEmpty(token)) {
            // 去除JWT前缀
            token = token.substring(JwtTokenUtils.TOKEN_PREFIX.length());
            return RedisUtils.hasKey(token);
        }
        return false;
    }

    /**
     * 从Redis中获取用户名
     *
     * @param token Token信息
     * @return
     */
    public static String getUserNameByToken(String token) {
        if (StringUtils.isNotEmpty(token)) {
            // 去除JWT前缀
            token = token.substring(JwtTokenUtils.TOKEN_PREFIX.length());
            return RedisUtils.hget(token, "username").toString();
        }
        return null;
    }

    /**
     * 从Redis中获取过期时间
     *
     * @param token Token信息
     * @return 过期时间，字符串
     */
    public static String getExpirationByToken(String token) {
        if (StringUtils.isNotEmpty(token)) {
            // 去除JWT前缀
            token = token.substring(JwtTokenUtils.TOKEN_PREFIX.length());
            return RedisUtils.hget(token, "expiration").toString();
        }
        return null;
    }

    /**
     * 从Redis中获取IP
     *
     * @param token Token信息
     * @return
     */
    public static String getIpByToken(String token) {
        if (StringUtils.isNotEmpty(token)) {
            // 去除JWT前缀
            token = token.substring(JwtTokenUtils.TOKEN_PREFIX.length());
            return RedisUtils.hget(token, "ip").toString();
        }
        return null;
    }

    /**
     * 判断当前Token是否在黑名单中
     *
     * @param token Token信息
     */
    public static boolean isBlackList(String token) {
        if (StringUtils.isNotEmpty(token)) {
            // 去除JWT前缀
            token = token.substring(JwtTokenUtils.TOKEN_PREFIX.length());
            return RedisUtils.hasKey("blackList", token);
        }
        return false;
    }

    /**
     * 将Token放到黑名单中
     *
     * @param token Token信息
     */
    public static void addBlackList(String token) {
        if (StringUtils.isNotEmpty(token)) {
            // 去除JWT前缀
            token = token.substring(JwtTokenUtils.TOKEN_PREFIX.length());
            RedisUtils.hset("blackList", token, df.format(LocalDateTime.now()));
        }
    }

    /**
     * 从Redis中获取刷新时间
     *
     * @param token Token信息
     * @return 刷新时间，字符串
     */
    public static String getRefreshTimeByToken(String token) {
        if (StringUtils.isNotEmpty(token)) {
            // 去除JWT前缀
            token = token.substring(JwtTokenUtils.TOKEN_PREFIX.length());
            return RedisUtils.hget(token, "refreshTime").toString();
        }
        return null;
    }

    /**
     * Redis中删除Token
     *
     * @param token Token信息
     */
    public static void deleteRedisToken(String token) {
        if (StringUtils.isNotEmpty(token)) {
            //


            token = token.substring(JwtTokenUtils.TOKEN_PREFIX.length());
            RedisUtils.deleteKey(token);
        }
    }
}
