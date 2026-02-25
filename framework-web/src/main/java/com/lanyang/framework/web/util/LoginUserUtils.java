package com.lanyang.framework.web.util;

import com.lanyang.framework.redis.service.RedisService;
import com.lanyang.framework.web.constant.LoginCacheConstants;
import com.lanyang.framework.web.domain.LoginUser;
import com.lanyang.framework.web.exception.BizException;
import com.lanyang.framework.common.constant.SecurityConstants;
import com.lanyang.framework.common.context.TransmittableThreadLocalContextHolder;

/**
 * @author lanyang
 * @date 2025/4/15
 * @des
 */
public class LoginUserUtils {


    /**
     * 从ThreadLocal获取登录的管理用户信息
     */
    public static LoginUser getLoginUserFromLocal() {
        String token = ServletUtils.getToken();
        if (token == null) {
            throw new BizException(401, "请登录");
        }

        return SecurityUtils.getLoginUser();
    }

    /**
     * 从redis中获取登录用户
     * 登录时，将当前登录用户信息写入redis,key = 生成的随机唯一user-key，value = 登录用户
     * 网关过滤器中将登录时生成的唯一key，写到请求头
     * @return
     */
    public static LoginUser getLoginUserFromRedis() {
        String token = ServletUtils.getToken();
        if (token == null) {
            throw new BizException(401, "请登录");
        }

        String userKey = ServletUtils.getUserKey();
        if (token == null) {
            throw new BizException(401, "请登录");
        }
        RedisService redisService = SpringUtils.getBean(RedisService.class);
        return redisService.getCacheObject(LoginCacheConstants.LOGIN_TOKEN_KEY + userKey);
    }

    public static Long getUserId() {
        LoginUser loginUser = TransmittableThreadLocalContextHolder.getObject(SecurityConstants.LOGIN_USER);
        if(loginUser == null){
            return null;
        }
        return loginUser.getUserId();
    }

    public static String getUsername() {
        LoginUser loginUser = TransmittableThreadLocalContextHolder.getObject(SecurityConstants.LOGIN_USER);
        if(loginUser == null){
            return null;
        }
        return loginUser.getUsername();
    }

    public static boolean isSuperAdmin(){
        LoginUser loginUser = TransmittableThreadLocalContextHolder.getObject(SecurityConstants.LOGIN_USER);
        if(loginUser == null){
            return false;
        }

        return loginUser.getSuperAdmin();
    }

    public static boolean isTenantAdmin(){
        LoginUser loginUser = TransmittableThreadLocalContextHolder.getObject(SecurityConstants.LOGIN_USER);
        if(loginUser == null){
            return false;
        }

        return loginUser.getTenantAdmin();
    }
}
