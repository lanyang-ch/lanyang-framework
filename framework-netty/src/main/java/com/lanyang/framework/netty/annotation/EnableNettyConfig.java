package com.lanyang.framework.netty.annotation;

import com.lanyang.framework.netty.config.NettyConfig;
import com.lanyang.framework.netty.config.NettyProperties;
import com.lanyang.framework.netty.initializer.NettyInitializer;
import com.lanyang.framework.netty.initializer.NettyServer;
import com.lanyang.framework.netty.runner.NettyServerRunner;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lanyang
 * @date 2025/12/11
 * @des
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({NettyServerRunner.class, NettyInitializer.class, NettyConfig.class, NettyProperties.class, NettyServer.class})
public @interface EnableNettyConfig {
}
