package com.lanyang.framework.web.config;

import com.lanyang.framework.web.interceptor.GlobalFeignRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lanyang
 * @date 2025/4/17
 * @des
 */
@Configuration
public class FeignRequestConfig {

    @Bean
    public RequestInterceptor requestInterceptor(){
        return new GlobalFeignRequestInterceptor();
    }
}
