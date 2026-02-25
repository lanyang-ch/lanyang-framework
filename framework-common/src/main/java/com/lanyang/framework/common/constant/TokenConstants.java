package com.lanyang.framework.common.constant;

/**
 * @author lanyang
 * @date 2025/3/24
 * @des 请求头参数常量
 */
public class TokenConstants {

    /**
     * 令牌自定义标识
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * 令牌前缀
     */
    public static final String PREFIX = "Bearer ";

    /**
     * 令牌秘钥
     */
    public static final String SECRET = "abcdefghijklmnopqrstuvwxyz";

    /** 默认过期时间 2小时 单位ms */
    public static final long DEFAULT_EXPIRE = 2 * 60 * 60 * 1000L;
    /** 不过期 */
    public static final long NOT_EXPIRE = -1L;

    /** 默认过期key */
    public static final String EXPIRE_KEY = "expire";

    /**
     * 登录平台
     */
    public static final String PLATFORM = "uni-platform";


}
