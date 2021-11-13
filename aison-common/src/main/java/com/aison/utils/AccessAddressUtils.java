package com.aison.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * TODO
 * 获取请求IP地址工具类
 * @author hyb
 * @date 2021/9/26 9:38
 */
@Component
public class AccessAddressUtils {
    /**
     * 获取真实IP
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) throws UnknownHostException {
//        String ip = request.getHeader("x-forwarded-for");
        InetAddress addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress();
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
