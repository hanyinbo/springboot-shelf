package com.aison.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * TODO
 * 登录验证
 *
 * @author hyb
 * @date 2021/9/22 14:36
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
//        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            try (InputStream is = request.getInputStream()) {
//                AuthenticationBean authenticationBean = mapper.readValue(is,AuthenticationBean.class);
                authRequest = new UsernamePasswordAuthenticationToken(
                        username, password);
            } catch (IOException e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken(
                        "", "");
            } finally {
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
//        }
//        else {
//            return super.attemptAuthentication(request, response);
//        }
    }
}
