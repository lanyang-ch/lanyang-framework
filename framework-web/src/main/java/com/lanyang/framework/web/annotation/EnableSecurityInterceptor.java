package com.lanyang.framework.web.annotation;

import com.lanyang.framework.web.aspect.UserAuthAspect;
import com.lanyang.framework.web.config.SecurityAutoConfig;
import com.lanyang.framework.web.config.WebConfig;
import com.lanyang.framework.web.interceptor.SecurityInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lanyang
 * @date 2025/3/27
 * @des 引入统一拦截器，拦截用户信息
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({WebConfig.class, SecurityInterceptor.class, UserAuthAspect.class, SecurityAutoConfig.class})
public @interface EnableSecurityInterceptor {
}
