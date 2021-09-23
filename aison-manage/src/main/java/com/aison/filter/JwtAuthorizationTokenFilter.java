//package com.aison.filter;
//
//import com.aison.utils.JwtTokenUtils;
//import io.jsonwebtoken.ExpiredJwtException;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * TODO
// *
// * @author hyb
// * @date 2021/9/22 14:30
// */
//@AllArgsConstructor
//@Component
//public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private  UserDetailsService userDetailsService;
//
//    private JwtTokenUtils jwtTokenUtils;
//
//    private  String tokenHeader;
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        final String requestHeader = httpServletRequest.getHeader(this.tokenHeader);
//        String username = null;
//        String authToken = null;
//        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
//            authToken = requestHeader.substring(7);
//            try {
//                //username = jwtTokenUtil.getUsernameFromToken(authToken);
//            } catch (ExpiredJwtException e) {
//            }
//        }
//    }
//}
