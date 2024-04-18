package com.love.common.util;

import com.love.common.exception.BizException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 对称加密算法AES/DES/TripleDES 摘要算法SHA1/SHA256/MD5 非对称加密算法RSA
 */
public class EncryptUtil {

    private static final Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

    private static final String COMMON_DES_KEY = "keepstudyingwhenuralive!";

    private static final String COMMON_AES_KEY = "workhardkileabee";

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * AES加密算法,推荐使用这种加密算法，性能和安全性都比DES要高
     *
     * @param src 明文
     * @return String 密文
     */
    public static String aesEncrypt(String src) {
        try {
            return aesEncrypt(src, COMMON_AES_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES加密算法,推荐使用这种加密算法，性能和安全性都比DES要高
     *
     * @param src 明文
     * @param key 密码
     * @return String 密文
     * @throws Exception 加密异常
     */
    public static String aesEncrypt(String src, String key) throws Exception {

        src = URLEncoder.encode(src, UTF_8.name());

        // 判断Key是否为16位
        if (key == null || key.length() != 16) {
            return null;
        }

        byte[] raw = key.getBytes(UTF_8);
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        byte[] encrypted = cipher.doFinal(src.getBytes(UTF_8));
        StringBuilder sb = new StringBuilder();

        for (byte b : encrypted) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * AES解密
     *
     * @param content 密文
     * @return 明文
     */
    public static String aesDecrypt(String content) {
        try {
            return aesDecrypt(content, COMMON_AES_KEY);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw BizException.build("Validate token failed!");
        }
    }

    /**
     * AES解密算法
     *
     * @param src 密文
     * @param key 密码
     * @return 明文
     * @throws Exception 解密异常
     */
    public static String aesDecrypt(String src, String key) throws Exception {
        byte[] byteResult = new byte[src.length() / 2];
        for (int i = 0; i < src.length() / 2; i++) {
            int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);
            byteResult[i] = ( byte ) (high * 16 + low);
        }

        // 判断Key是否为16位
        if (key == null || key.length() != 16) {
            return null;
        }

        byte[] raw = key.getBytes(UTF_8);
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        byte[] original = cipher.doFinal(byteResult);
        String originalString = new String(original, UTF_8);
        return URLDecoder.decode(originalString, UTF_8.name());
    }

    /**
     * DES加密
     *
     * @param src 明文
     * @return String 密文
     */
    public static String desEncrypt(String src) {
        return desEncrypt(src, COMMON_DES_KEY);
    }

    /**
     * DES加密
     *
     * @param src 明文
     * @param key 密文
     * @return 密文
     */
    public static String desEncrypt(String src, String key) {
        SecretKey secretKeySpec = new SecretKeySpec(key.getBytes(), "DESede");
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] data = cipher.doFinal(src.getBytes());
            return new String(Base64.encodeBase64(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * DES解密
     *
     * @param content 密文
     * @return 明文
     */
    public static String desDecrypt(String content) {
        return desDecrypt(content, COMMON_DES_KEY);
    }

    /**
     * DES解密
     *
     * @param content 密文
     * @param key     密码
     * @return 明文
     */
    public static String desDecrypt(String content, String key) {
        try {
            SecretKey secretKeySpec = new SecretKeySpec(key.getBytes(), "DESede");
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] data = Base64.decodeBase64(content.getBytes(UTF_8));
            byte[] raw = cipher.doFinal(data);
            return new String(raw, UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * SHA1 算法
     *
     * @param string 明文
     * @return 密文
     */
    public static String sha1(String string) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            byte[] data = digest.digest(string.getBytes(UTF_8));
            StringBuilder hexValue = new StringBuilder();
            for (byte datum : data) {
                int val = (( int ) datum) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * SHA1
     *
     * @param string 明文
     * @return 密文
     */
    public static String _sha1(String string) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            digest.update(string.getBytes(UTF_8));
            byte[] data = digest.digest();
            int len = data.length;
            char[] buffer = new char[len * 2];
            int k = 0;
            for (byte d : data) {
                buffer[k++] = HEX_DIGITS[d >>> 4 & 0xf];
                buffer[k++] = HEX_DIGITS[d & 0xf];
            }
            return new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * SHA256
     *
     * @param string 明文
     * @return 密文
     */
    public static String sha256(String string) {
        try {
            MessageDigest digest;
            digest = MessageDigest.getInstance("SHA-256");
            digest.update(string.getBytes(UTF_8));
            return byte2hex(digest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes 数据
     * @return 字符结果
     */
    private static String byte2hex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        String temp = null;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                builder.append("0");
            }
            builder.append(temp);
        }
        return builder.toString();
    }

    /**
     * @param bytes data
     * @return result
     */
    private static char[] encodeHex(byte[] bytes) {
        char[] chars = new char[32];
        for (int i = 0; i < chars.length; i += 2) {
            byte b = bytes[i / 2];
            chars[i] = HEX_DIGITS[b >>> 4 & 15];
            chars[i + 1] = HEX_DIGITS[b & 15];
        }
        return chars;
    }

    /**
     * 32位的md5
     *
     * @param string 明文
     * @return 密文
     */
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

    /**
     * 16位的md5
     *
     * @param string 明文
     * @return 密文
     */
    public static String md5_16(String string) {
        String md5 = md5_32(string);
        if (md5 == null) {
            return null;
        }
        return md5.substring(8, 24);
    }

    /**
     * @param string 明文
     * @return 密文
     */
    public static String md5(String string) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(string.getBytes(UTF_8));
            byte[] data = digest.digest();
            // return new BigInteger(data).toString(16);
            // return new String(encodeHex(data));
            return byte2hex(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
