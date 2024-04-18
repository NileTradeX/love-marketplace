package com.love.common.util;


import java.security.MessageDigest;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Md5Util {

    public static String md5_32(String string) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(string.getBytes(UTF_8));
            byte[] data = digest.digest();
            StringBuilder result = new StringBuilder();
            for (byte datum : data) {
                result.append(Integer.toHexString((0x000000FF & datum) | 0xFFFFFF00).substring(6));
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
