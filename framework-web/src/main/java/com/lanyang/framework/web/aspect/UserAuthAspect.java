package com.lanyang.framework.web.aspect;

import com.lanyang.framework.web.annotation.RequiresLogin;
import com.lanyang.framework.web.annotation.RequiresPermissions;
import com.lanyang.framework.web.annotation.RequiresRoles;
import com.lanyang.framework.web.util.AuthUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author lanyang
 * @date 2025/4/15
 * @des 用户权限验证切面
 */
@Aspect
@Component
public class UserAuthAspect {


    @Pointcut("@annotation(com.lanyang.framework.web.annotation.RequiresLogin) " +
            "|| @annotation(com.lanyang.framework.web.annotation.RequiresPermissions) " +
            "|| @annotation(com.lanyang.framework.web.annotation.RequiresRoles)")
    public void pointcut() {
    }


    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        MethodSignature signature = (MethodSignature) point.getSignature();
        checkMethodAuthAnnotation(signature.getMethod());
        try {
            // 执行原有逻辑
            Object obj = point.proceed();
            return obj;
        } catch (Throwable e) {
            throw e;
        }
    }

    /**
     * 校验权限
     * @param method
     */
    public void checkMethodAuthAnnotation(Method method) {
        //校验 @RequiresLogin 注解
        RequiresLogin requiresLogin = method.getAnnotation(RequiresLogin.class);
        if (requiresLogin != null) {
            AuthUtils.checkLogin();
        }

        //校验 @RequiresRoles 注解
        RequiresRoles requiresRoles = method.getAnnotation(RequiresRoles.class);
        if (requiresRoles != null) {
            AuthUtils.checkRole(requiresRoles);
        }

        //校验 @RequiresPermissions 注解
        RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
        if (requiresPermissions != null) {
            AuthUtils.checkPermissions(requiresPermissions);
        }
    }

}
