//package com.aison.Interceptor;
//
//import com.aison.common.Msg;
//import com.aison.common.Result;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.jsonwebtoken.*;
//import lombok.SneakyThrows;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.PrintWriter;
//
//@Component
//public class JwtTokenInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        //因为是在请求头中发送token，所以第一次请求的方法为"OPTIONS"，具体可以看TCP/IP协议
//        String method = request.getMethod();
//        if("OPTIONS".equalsIgnoreCase(method)){
//            return true;
//        }
//        String token = request.getHeader("token");
//        System.out.println("token:"+token);
//        if(token == null){
//            Result result = new Result(Msg.LOGIN_FAIL,Msg.LOGIN_USER_PWD_ERROR_FAIL,null);
////            ResultVO resultVO = new ResultVO(ResStatus.NO,"请先登录",null);
//            doResponse(response,result);
//        }else{
//            try{
//                //在jwt中，只要token不合法或者验证不通过就会抛出异常
//                JwtParser parser = Jwts.parser();
//                parser.setSigningKey("123456");
//                Jws<Claims> claimsJws = parser.parseClaimsJws(token);
//                return true;
//            }catch (ExpiredJwtException e1) {
//                Result resultVO = new Result(Msg.LOGIN_FAIL,Msg.LOGIN_USER_PWD_ERROR_FAIL,null);
////                ResultVO resultVO = new ResultVO(ResStatus.PASS, "登录过期，请重新登录", null);
//                doResponse(response,resultVO);
//            }catch (UnsupportedJwtException e2){
//                Result resultVO = new Result(Msg.LOGIN_FAIL,Msg.LOGIN_USER_PWD_ERROR_FAIL,null);
////                ResultVO resultVO = new ResultVO(ResStatus.NO, "Token不合法,已记录恶意IP", null);
//                doResponse(response,resultVO);
//            }catch (Exception e3){
//                Result resultVO = new Result(Msg.LOGIN_FAIL,Msg.LOGIN_USER_PWD_ERROR_FAIL,null);
////                ResultVO resultVO = new ResultVO(ResStatus.NO, "请先登录", null);
//                doResponse(response,resultVO);
//            }
//        }
//        return false;
//    }
//
//    @SneakyThrows
//    private void doResponse(HttpServletResponse response, Result resultVO) {
//        response.setContentType("application/json");
//        response.setCharacterEncoding("utf-8");
//        PrintWriter out = response.getWriter();
//        String s = new ObjectMapper().writeValueAsString(resultVO);
//        out.print(s);
//        out.flush();
//        out.close();
//    }
//}
