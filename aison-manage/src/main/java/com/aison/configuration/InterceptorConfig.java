//package com.aison.configuration;
//
//import com.aison.Interceptor.JwtTokenInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class InterceptorConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authenticationInterceptor())
////                .addPathPatterns("/**")
//                .excludePathPatterns("/login",
//                        "/logout",
//                        "/css/**",
//                        "/js/**",
//                        "index.html",
//                        "favicon.ico",
//                        "/doc.html",
//                        "/webjars/**",
//                        "/swagger-resources/**",
//                        "/v2/api-docs/**",
//                        "/captcha");
//    }
//}
