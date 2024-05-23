package com.common.utils.jwt;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class JwtUtil {

    private static final String SIGN_KEY = "SomersaultCloud";
    //    private static final Long EXPIRE = 43200000L; //12h
    private static final Long EXPIRE = 99999999999999999L; //TODO 待改 jwt有效时间 2592000000L

    /**
     * 防止实例化
     */
    private JwtUtil(){}

    /**
     * 生成JWT令牌
     * @param claims JWT第二部分负载 payload 中存储的内容
     * @return 用户token
     */
    public static String createJwt(Map<String, Object> claims){
        return Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SIGN_KEY.getBytes())  //go的jwt数字签名不支持字符串
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .compact();
    }

    /**
     * 解析JWT令牌
     * @param jwt JWT令牌
     * @return claims内容的键值对
     * Key:    uid  用户ID
     */
    public static Map<String,Object> parseJwt(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(SIGN_KEY.getBytes())
                .parseClaimsJws(jwt)
                .getBody();
        return new HashMap<>(claims);
    }

    /**
     * 直接通过jwt令牌得到uid
     * @param jwt 令牌
     * @return 用户id
     */
    public static Integer getUidByJwt(String jwt){
        //判断是否jwt正确
        if(!isValidJwt(jwt)) return null;

        Claims claims = Jwts.parser()
                .setSigningKey(SIGN_KEY.getBytes())
                .parseClaimsJws(jwt)
                .getBody();
        return (Integer) claims.get("uid");
    }


    /**
     * 判断令牌是否有效
     * @param jwt 令牌
     * @return boolean
     */
    public static boolean isValidJwt(String jwt) {

        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SIGN_KEY.getBytes())
                    .parseClaimsJws(jwt);

            Claims jwtClaims = claims.getBody();
            //测试用户无过期时间 特判
            if(jwtClaims.getExpiration() == null) return true;

            return !jwtClaims.getExpiration().before(new Date());
        } catch (Exception e) {
            // Invalid JWT signature
            return false;
        }

    }
}