package com.lanyang.framework.web.interceptor;

import com.lanyang.framework.common.constant.SecurityConstants;
import com.lanyang.framework.web.annotation.DataScope;
import com.lanyang.framework.web.annotation.RequiresLogin;
import com.lanyang.framework.web.annotation.RequiresPermissions;
import com.lanyang.framework.web.annotation.RequiresRoles;
import com.lanyang.framework.web.domain.LoginUser;
import com.lanyang.framework.web.handler.LoginUserHandler;
import com.lanyang.framework.web.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lanyang
 * @date 2025/3/27
 * @des
 */
@RequiredArgsConstructor
public class SecurityInterceptor implements HandlerInterceptor {

    private final LoginUserHandler loginUserHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        //用户id
        String userId = request.getHeader(SecurityConstants.USER_ID);
        if(StringUtils.isNotBlank(userId)){
            SecurityUtils.setUserId(userId);
        }

        //租户信息
        String tenantId = request.getHeader(SecurityConstants.TENANT_ID);
        if(StringUtils.isNotBlank(tenantId)){
            SecurityUtils.setTenantId(tenantId);
        }

        if(handler instanceof HandlerMethod) {
            RequiresLogin requiresLogin = ((HandlerMethod) handler).getMethodAnnotation(RequiresLogin.class);
            RequiresRoles requiresRoles = ((HandlerMethod) handler).getMethodAnnotation(RequiresRoles.class);
            RequiresPermissions requiresPermissions = ((HandlerMethod) handler).getMethodAnnotation(RequiresPermissions.class);
            DataScope dataScope = ((HandlerMethod) handler).getMethodAnnotation(DataScope.class);

            if(requiresLogin != null || requiresRoles != null || requiresPermissions != null || dataScope != null){
                //需要做权限检验的访问资源，将登录信息写入ThreadLocal，在权限检验时，从ThreadLocal取用户角色和权限码
                LoginUser loginUser = loginUserHandler.getLoginUser();
                SecurityUtils.setLoginUser(loginUser);
            }
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        SecurityUtils.remove();
    }
}
