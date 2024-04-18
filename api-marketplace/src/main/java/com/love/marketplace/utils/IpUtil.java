package com.love.marketplace.utils;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return filterIp(ip);
    }

    private static String filterIp(String ip) {
        if (ip != null) {
            String[] data = ip.split(",");

            for (int i = 0; i < data.length; ++i) {
                if (!"unknown".equalsIgnoreCase(data[i].replaceAll("\\s*", ""))) {
                    ip = data[i].replaceAll("\\s*", "");
                    break;
                }
            }
        }

        return ip;
    }
}
