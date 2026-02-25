package com.lanyang.framework.web.annotation;

import com.lanyang.framework.web.interceptor.GlobalFeignRequestInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lanyang
 * @date 2025/4/17
 * @des 是否开启全局feign拦截器 开启会引入GlobalFeignRequestInterceptor做全局请求拦截
 *      同时指定feign的扫描路径包com.lanyang.cloud，项目在引入其他项目feign模块时，可以不再指定扫描路径
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({GlobalFeignRequestInterceptor.class})
@EnableFeignClients(basePackages = "com.lanyang")
public @interface EnableGlobalFeignInterceptor {
}
