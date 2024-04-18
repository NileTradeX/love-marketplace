package com.love.common;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final String S3_HOST = "https://s3.us-west-1.amazonaws.com/love.files/";
    public static final String GOODS_URL_PREFIX = "https://www.love.com/shop/product/";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_CST = "EEE MMM dd HH:mm:ss zzz yyyy";
    public static final String DATE_FORMAT_T = "yyyy-MM-dd'T'HH:mm:ss";

    public static final String UID = "uid";
    public static final String USER_ID = "userId";
    public static final String ROLE_PERMS = "role:perms:";
    public static final String MER_USER_INVITATION = "mer:user:invitation:";
    public static final String KEY_FREE_GIFT_CONFIG = "free.gift.config";
    public static final String REDIS_KEY_FREE_GIFT_PRE = "free:gift:promo:";
    public static final Map<String, Object> FAKE_MAP = new HashMap<>();
    public static final String MERCHANT_NAME = "merchant:name:";

    static {
        FAKE_MAP.put(UID, "0");
        FAKE_MAP.put(USER_ID, 0);
    }
}
