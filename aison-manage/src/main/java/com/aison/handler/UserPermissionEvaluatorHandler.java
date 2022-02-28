//package com.aison.handler;
//
//import com.aison.authority.ManageUserDetailServiceImpl;
//import com.aison.authority.ManageUserDetails;
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.PermissionEvaluator;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import java.io.Serializable;
//import java.util.HashSet;
//import java.util.Set;
//
///**
//  *  用户权限注解处理类
//  * @author hyb
//　* @date 2022/2/28 11:55
//  */
//@Slf4j
//@Component
//public class UserPermissionEvaluatorHandler implements PermissionEvaluator {
//    @Autowired
//    private ManageUserDetailServiceImpl manageUserDetailService;
//
//    @Override
//    public boolean hasPermission(Authentication authentication, Object o, Object opermission) {
//        log.info("用户权限注解处理类(入参)："+ JSONObject.toJSONString(opermission));
//        ManageUserDetails userDetails = (ManageUserDetails)authentication.getPrincipal();
//        log.info("用户权限注解处理类,获取用户信息："+ JSONObject.toJSONString(userDetails));
//        Set<String> permissionList= new HashSet<>(); // 用户权限
//
//        ManageUserDetails userInfo = manageUserDetailService.loadUserByUsername(userDetails.getUsername());
//        Set<? extends GrantedAuthority> authoritiesSet = userInfo.getAuthorities();
//        authoritiesSet.forEach(auth ->{
//            permissionList.add(auth.getAuthority());
//        });
//        // 判断是否拥有权限
//        log.info("判断是否拥有权限："+ JSONObject.toJSONString(permissionList));
//        if (permissionList.contains(opermission.toString())) {
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
//        return false;
//    }
//}
