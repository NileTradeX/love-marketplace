package com.love.api.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    public final static String UID = "uid";

    public final static String ISSUER = "HelloWorld";

    public final static String SECRET = "workhard";


    public final static int DEF_EXPIRED_TIME = 60 * 60 * 12 * 1000;

    /**
     * 创建JWT
     */
    public static String createJwt(String uid) throws IllegalArgumentException, JWTCreationException {
        return createJwt(uid, ISSUER, SECRET, DEF_EXPIRED_TIME);
    }

    public static String createJwt(Map<String, Object> map) throws IllegalArgumentException, JWTCreationException {
        return createJwt(map, ISSUER, SECRET, DEF_EXPIRED_TIME);
    }


    /**
     * 创建JWT
     */
    public static String createJwt(String uid, long expire) throws IllegalArgumentException, JWTCreationException {
        return createJwt(uid, ISSUER, SECRET, expire);
    }

    public static String createJwt(Map<String, Object> map, long expire) throws IllegalArgumentException, JWTCreationException {
        return createJwt(map, ISSUER, SECRET, expire);
    }


    /**
     * 创建JWT
     */
    public static String createJwt(String uid, String issuer, String secret, long expire) throws IllegalArgumentException, JWTCreationException {
        return JWT.create().withIssuer(issuer).withClaim(UID, uid).withExpiresAt(new Date(System.currentTimeMillis() + expire)).sign(Algorithm.HMAC256(secret));
    }

    public static String createJwt(Map<String, Object> map, String issuer, String secret, long expire) throws IllegalArgumentException, JWTCreationException {
        JWTCreator.Builder builder = JWT.create().withIssuer(issuer);
        map.forEach((k, v) -> builder.withClaim(k, v.toString()));
        return builder.withExpiresAt(new Date(System.currentTimeMillis() + expire)).sign(Algorithm.HMAC256(secret));
    }

    /**
     * 验证jwt
     */
    public static Map<String, String> verifyJwt(String token, String issuer, String secret) throws JWTVerificationException {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret)).withIssuer(issuer).build().verify(token);

        Map<String, Claim> claims = decodedJWT.getClaims();
        Map<String, String> result = new HashMap<>(claims.size());
        claims.forEach((k, v) -> {
            String str = v.as(String.class);
            result.put(k, str);
        });
        return result;
    }

    public static Map<String, String> verifyJwt(String token) throws JWTVerificationException {
        return verifyJwt(token, ISSUER, SECRET);
    }
}
