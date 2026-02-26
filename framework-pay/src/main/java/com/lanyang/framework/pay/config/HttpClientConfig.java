package com.lanyang.framework.pay.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * HTTP客户端配置
 * 支持通过配置文件 pay.http-client.* 进行配置
 * 
 * @author lanyang
 * @date 2026/1/8
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(PayHttpClientProperties.class)
public class HttpClientConfig {

    private final PayHttpClientProperties httpClientProperties;

    /**
     * 创建 OkHttpClient Bean
     * 配置连接池、超时时间等参数，优化性能和稳定性
     * 配置项可通过 pay.http-client.* 进行自定义，未配置则使用默认值
     */
    @Bean
    @ConditionalOnMissingBean(name = "okHttpClient")
    public OkHttpClient okHttpClient() {
        // 连接池配置
        ConnectionPool connectionPool = new ConnectionPool(
                httpClientProperties.getMaxIdleConnections(),
                httpClientProperties.getKeepAliveDuration(),
                TimeUnit.MINUTES
        );
        
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                // 连接超时时间
                .connectTimeout(httpClientProperties.getConnectTimeout(), TimeUnit.SECONDS)
                // 读取超时时间（微信支付接口可能需要较长时间）
                .readTimeout(httpClientProperties.getReadTimeout(), TimeUnit.SECONDS)
                // 写入超时时间
                .writeTimeout(httpClientProperties.getWriteTimeout(), TimeUnit.SECONDS)
                // 连接池：复用连接，提高性能
                .connectionPool(connectionPool)
                // 失败重试：自动重试幂等请求
                .retryOnConnectionFailure(httpClientProperties.isRetryOnConnectionFailure())
                .build();
        
        log.info("OkHttpClient 配置完成 - 连接超时: {}s, 读取超时: {}s, 写入超时: {}s, " +
                        "最大空闲连接数: {}, 连接保持时间: {}分钟, 重试连接失败: {}",
                httpClientProperties.getConnectTimeout(),
                httpClientProperties.getReadTimeout(),
                httpClientProperties.getWriteTimeout(),
                httpClientProperties.getMaxIdleConnections(),
                httpClientProperties.getKeepAliveDuration(),
                httpClientProperties.isRetryOnConnectionFailure());
        
        return okHttpClient;
    }
}
