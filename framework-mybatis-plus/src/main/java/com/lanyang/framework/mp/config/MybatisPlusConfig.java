package com.lanyang.framework.mp.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.lanyang.framework.mp.handler.CustomTenantHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lanyang
 * @date 2025/4/30
 * @des
 */
@Configuration
@EnableConfigurationProperties(TenantProperties.class)
@RequiredArgsConstructor
public class MybatisPlusConfig {

    private final TenantProperties properties;

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptoyr() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        if (Boolean.TRUE.equals(properties.getEnable())) {
            //租户插件
            interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new CustomTenantHandler(properties)));
        }
        return interceptor;
    }
}
