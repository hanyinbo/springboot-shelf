package com.aison.utils;

import com.aison.dto.UserDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import io.jsonwebtoken.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * jwt工具类
 *
 * @author aison
 * @version 1.0
 * @date 2019-10-24 11:13
 */
@Component
@ConfigurationProperties(prefix = "jwt")
@Data
@Slf4j
public class JwtTokenUtils {

    /**
     * header名称
     */
    public  static String TOKENHEADER="Authorization";
    public static final String TOKENPREFIX = "Bearer ";
    public static final String SECRET = "aisonjwt";
    public static final Long EXPIRATION = 7200L;
    public static final Long REMEMBEEXPIRATON=14400L;
    /**
     * token前缀
     */
    public   String tokenPrefix;
    /**
     * 秘钥
     */
    public static String secret;
    /**
     * 过期时间
     */
    public static Long expiration;
    /**
     * 选择记住后过期时间
     */
    public static Long rememberExpiraton;


    /**
     * 生成token
     *
     * @param uSerDTO
     * @return
     */
//    public String createToken(UserDTO uSerDTO) {
////        Long time = uSerDTO.getRemember() ? this.rememberExpiraton : this.expiration;
//        Long time = uSerDTO.getRemember() ? this.REMEMBEEXPIRATON : this.EXPIRATION;
//
//        Map<String, Object> map = new HashMap<>(1);
//        map.put("user", uSerDTO);
//        return Jwts.builder()
//                .setClaims(map)
//                .setSubject(uSerDTO.getUsername())
//                .setExpiration(new Date(System.currentTimeMillis() + time * 1000))
//                .signWith(SignatureAlgorithm.HS512, SECRET)
//                .compact();
//    }
    // 创建token
    public static String createToken(String username, boolean isRememberMe) {
        long expiration = isRememberMe ? REMEMBEEXPIRATON : EXPIRATION;
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }
    /**
     * 解析token
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
     * 获取userDTO
     *
     * @param token
     * @return
     */
    public UserDTO getUserDTO(String token) {
        Claims claims = generateToken(token);
        Map<String, String> map = claims.get("user", Map.class);
        UserDTO userDTO = JSON.parseObject(JSON.toJSONString(map), UserDTO.class);
        return userDTO;
    }

    /**
     * 获取用户角色的权限列表, 没有返回空
     *
     * @param token
     * @return
     */
    public static List<SimpleGrantedAuthority> getUserAuth(String token) {

        String json = JSONArray.toJSONString("admin");
        List<SimpleGrantedAuthority> grantedAuthorityList = JSON.parseArray(json, SimpleGrantedAuthority.class);
        return grantedAuthorityList;
    }
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
    public static boolean isExpiration(String token){
        try{
            return getTokenBody(token).getExpiration().before(new Date());
        } catch(Exception e){
           e.printStackTrace();
        }
        return true;
    }

    public static void main(String[] args) {

        System.out.println("变量："+SECRET);
    }

}
