package com.lanyang.framework.common.utils;

import com.lanyang.framework.common.constant.SecurityConstants;
import com.lanyang.framework.common.constant.TokenConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * @author lanyang
 * @date 2025/3/24
 * @des
 */
public class JwtUtils {

    private static String secret = TokenConstants.SECRET;

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    @Deprecated
    public static String createToken(Map<String, Object> claims) {
        if(claims.containsKey(TokenConstants.EXPIRE_KEY)){
            long expireTime = Long.parseLong(claims.get(TokenConstants.EXPIRE_KEY).toString());
            //如果是-1则不处理
            if(expireTime != TokenConstants.NOT_EXPIRE){
                //设置过期时间
                claims.put(TokenConstants.EXPIRE_KEY, System.currentTimeMillis() + TokenConstants.DEFAULT_EXPIRE);
            }
        }else{
            //默认过期时间
            claims.put(TokenConstants.EXPIRE_KEY, System.currentTimeMillis() + TokenConstants.DEFAULT_EXPIRE);

        }
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 创建令牌
     *
     * @param claims 数据声明
     * @param expireTime 过期时间 单位为秒
     * @return 令牌
     */
    public static String createToken(Map<String, Object> claims, long expireTime) {

        if(expireTime <= 0){
            throw new IllegalArgumentException("过期时间必须大于0");
        }
        claims.put(TokenConstants.EXPIRE_KEY, System.currentTimeMillis() + expireTime * 1000L);
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public static Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * token是否过期
     * @param claims
     * @return 过期返回true
     */
    @Deprecated
    public static boolean isExpired(Claims claims) {
        String expire = Optional.ofNullable(claims.get(TokenConstants.EXPIRE_KEY).toString()).orElse(StringUtils.EMPTY);
        if(StringUtils.isBlank(expire)){
            return true;
        }
        long expireTime = Long.parseLong(expire);
        if(expireTime == TokenConstants.NOT_EXPIRE){
            return false;
        }
        if(System.currentTimeMillis() <= expireTime){
            return false;
        }
        return true;
    }

    /**
     * 获取用户登录唯一key
     * @param claims
     * @return
     */
    public static String getUserKey(Claims claims) {
        Object userKey = claims.get(SecurityConstants.USER_KEY);
        return userKey == null ? StringUtils.EMPTY : userKey.toString();
    }

    /**
     * 根据令牌获取用户名
     *
     * @param claims
     * @return 用户名
     */
    public static String getUserName(Claims claims) {
        return claims.get(SecurityConstants.USERNAME) == null ? StringUtils.EMPTY : claims.get(SecurityConstants.USERNAME).toString();
    }
}
