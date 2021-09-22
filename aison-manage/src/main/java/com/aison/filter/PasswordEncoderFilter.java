//package com.aison.filter;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//
////@Component
//public class PasswordEncoderFilter implements PasswordEncoder {
//
//    @Override
//    public String encode(CharSequence charSequence) {
//        //加密方法可以根据自己的需要修改
//        return charSequence.toString();
//    }
//
//    @Override
//    public boolean matches(CharSequence charSequence, String s) {
//        return encode(charSequence).equals(s);
//    }
//}
