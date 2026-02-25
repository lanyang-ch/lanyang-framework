package com.lanyang.framework.web.util;

import cn.hutool.core.convert.Convert;
import com.lanyang.framework.redis.service.RedisService;
import com.lanyang.framework.web.constant.LoginCacheConstants;
import com.lanyang.framework.web.domain.LoginUser;
import com.lanyang.framework.web.exception.BizException;
import com.lanyang.framework.common.constant.SecurityConstants;
import com.lanyang.framework.common.constant.TokenConstants;
import com.lanyang.framework.common.context.TransmittableThreadLocalContextHolder;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lanyang
 * @date 2025/4/15
 * @des
 */
public class SecurityUtils {

    private static void set(String key, Object value) {
        TransmittableThreadLocalContextHolder.set(key, value);
    }

    private static String get(String key) {
        return TransmittableThreadLocalContextHolder.get(key);
    }

    private static <T> T getObject(String key) {
        return TransmittableThreadLocalContextHolder.getObject(key);
    }

    /**
     * 获取租户ID
     */
    public static Long getTenantId() {
        return Convert.toLong(get(SecurityConstants.TENANT_ID), 0L);
    }

    /**
     * 设置租户ID
     */
    public static void setTenantId(String tenantId) {
        set(SecurityConstants.TENANT_ID, tenantId);
    }

    /**
     * 获取会员ID
     */
    public static Long getMemberId() {
        return Convert.toLong(get(SecurityConstants.MEMBER_ID));
    }

    public static void setMemberId(String memberId) {
        set(SecurityConstants.MEMBER_ID, memberId);
    }

    /**
     * 获取部门ID
     */
    public static Long getDeptId() {
        return Convert.toLong(get(SecurityConstants.DEPT_ID));
    }

    public static void setDeptId(String deptId) {
        set(SecurityConstants.DEPT_ID, deptId);
    }

    /**
     * 获取用户ID
     */
    public static Long getUserId() {
        return Convert.toLong(get(SecurityConstants.USER_ID));
    }
    public static void setUserId(String userId) {
        set(SecurityConstants.USER_ID, userId);
    }

    /**
     * 获取用户的微信openID
     */
    public static String getOpenId() {
        return get(SecurityConstants.OPEN_ID);
    }

    public static void setOpenId(String openId) {
        set(SecurityConstants.OPEN_ID, openId);
    }

    /**
     * 获取用户名称
     */
    public static String getUsername() {
        return get(SecurityConstants.USERNAME);
    }

    public static void setUsername(String username) {
        set(SecurityConstants.USERNAME, username);
    }

    /**
     * 获取用户key
     */
    public static String getUserKey() {
        return get(SecurityConstants.USER_KEY);
    }

    /**
     * 获取operatorId
     */
    public static Long getOperatorId(){
        return Convert.toLong(get(SecurityConstants.OPERATOR_ID));
    }

    public static void setOperatorId(Long operatorId) {
        set(SecurityConstants.OPERATOR_ID, operatorId);
    }

    /**
     * 获取登录用户信息
     */
    public static LoginUser getLoginUser() {
        return getObject(SecurityConstants.LOGIN_USER);
    }

    public static void setLoginUser(LoginUser loginUser) {
        set(SecurityConstants.LOGIN_USER, loginUser);
    }

    /**
     * 从redis中获取登录用户
     * 登录时，将当前登录用户信息写入redis,key = 生成的随机唯一user-key，value = 登录用户
     * 网关过滤器中将登录时生成的唯一key，写到请求头
     * @return
     */
    public static LoginUser getLoginUserFromRedis() {
        String token = ServletUtils.getToken();
        if (StringUtils.isBlank(token)) {
            throw new BizException(401, "请登录");
        }

        String userKey = ServletUtils.getUserKey();
        if (StringUtils.isBlank(userKey)) {
            throw new BizException(401, "请登录");
        }
        RedisService redisService = SpringUtils.getBean(RedisService.class);
        LoginUser loginUser = redisService.getCacheObject(LoginCacheConstants.LOGIN_TOKEN_KEY + userKey);
        if(loginUser == null){
            throw new BizException(401, "请登录");
        }
        return loginUser;
    }

    /**
     * 获取请求token
     */
    public static String getToken() {
        return getToken(ServletUtils.getRequest());
    }

    public static String getLoginPlatform() {
        return getLoginPlatform(ServletUtils.getRequest());
    }

    /**
     * 根据request获取请求token
     */
    public static String getToken(HttpServletRequest request) {
        // 从header获取token标识
        String token = request.getHeader(TokenConstants.AUTHORIZATION);
        return replaceTokenPrefix(token);
    }

    /**
     * 根据request获取请求来源
     */
    public static String getLoginPlatform(HttpServletRequest request) {
        return request.getHeader(TokenConstants.PLATFORM);
    }

    /**
     * 裁剪token前缀
     */
    public static String replaceTokenPrefix(String token) {
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX)) {
            token = token.replaceFirst(TokenConstants.PREFIX, StringUtils.EMPTY);
        }
        return token;
    }

    public static void remove() {
        TransmittableThreadLocalContextHolder.remove();
    }

}
