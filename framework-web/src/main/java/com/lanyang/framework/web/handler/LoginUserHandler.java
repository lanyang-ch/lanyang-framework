package com.lanyang.framework.web.handler;


import com.lanyang.framework.web.domain.LoginUser;
import com.lanyang.framework.web.util.SecurityUtils;

/**
 * @author lanyang
 * @date 2025/12/4
 * @des
 */
public interface LoginUserHandler {

    /**
     * 获取登录用户信息
     * 默认实现是从redis中获取
     * 各自服务可以重写实现逻辑
     * @return
     */
    default LoginUser getLoginUser() {
        return SecurityUtils.getLoginUserFromRedis();
    }
}
