package com.lanyang.framework.mp.aspect;

import com.lanyang.framework.mp.annotation.TenantIgnore;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author lanyang
 * @date 2025/5/6
 * @des
 */
@Aspect
@Slf4j
@Component
public class TenantIgnoreAspect {

    @Pointcut("@annotation(com.lanyang.framework.mp.annotation.TenantIgnore)"
            + "|| @within(com.lanyang.framework.mp.annotation.TenantIgnore)")
    public void pointcut(){

    }

    public TenantIgnoreAspect() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        try {
            Class<?> targetClass = point.getTarget().getClass();
            TenantIgnore classIgnoreTenant = targetClass.getAnnotation(TenantIgnore.class);
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            TenantIgnore methodIgnoreTenant = method.getAnnotation(TenantIgnore.class);

            //判断类上是否有注解
            boolean isClassAnnotated = AnnotationUtils.isAnnotationDeclaredLocally(TenantIgnore.class, targetClass);
            //判断方法上是否有注解
            boolean isMethodAnnotated = Objects.nonNull(methodIgnoreTenant);

            //如果类上有
            if (isClassAnnotated) {
                MybatisTenantContext.set(classIgnoreTenant.ignore());
            }
            //如果方法上有 以方法上的为主
            if (isMethodAnnotated) {
                MybatisTenantContext.set(methodIgnoreTenant.ignore());
            }
            Object result = point.proceed();
            return result;
        } finally {
            MybatisTenantContext.clear();
        }
    }
}
