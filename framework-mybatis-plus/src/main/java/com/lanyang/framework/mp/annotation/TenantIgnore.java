package com.lanyang.framework.mp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lanyang
 * @date 2025/5/6
 * @des
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface TenantIgnore {

    /**
     * 是否忽略租户
     * true 忽略
     * false 不忽略
     * @return
     */
    boolean ignore() default true;
}
