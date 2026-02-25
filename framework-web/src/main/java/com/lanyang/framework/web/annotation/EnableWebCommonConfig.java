package com.lanyang.framework.web.annotation;

import com.lanyang.framework.swagger.annotation.EnableCommonSwagger;
import org.mybatis.spring.annotation.MapperScan;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lanyang
 * @date 2025/4/17
 * @des 开启web项目启动注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@MapperScan(basePackages = "com.lanyang.**.mapper")
@EnableCommonSwagger
@EnableGlobalFeignInterceptor
public @interface EnableWebCommonConfig {
}
