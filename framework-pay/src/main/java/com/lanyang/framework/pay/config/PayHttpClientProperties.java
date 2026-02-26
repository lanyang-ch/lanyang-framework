package com.lanyang.framework.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 支付模块 HTTP 客户端配置属性
 * 可通过配置文件 pay.http-client.* 进行配置
 * 
 * @author lanyang
 * @date 2026/1/8
 */
@Data
@ConfigurationProperties(prefix = "pay.http-client")
public class PayHttpClientProperties {
    
    /**
     * 连接超时时间（秒），默认10秒
     */
    private int connectTimeout = 10;
    
    /**
     * 读取超时时间（秒），默认30秒
     */
    private int readTimeout = 30;
    
    /**
     * 写入超时时间（秒），默认30秒
     */
    private int writeTimeout = 30;
    
    /**
     * 连接池最大空闲连接数，默认5
     */
    private int maxIdleConnections = 5;
    
    /**
     * 连接池保持连接时间（分钟），默认5分钟
     */
    private long keepAliveDuration = 5;
    
    /**
     * 是否重试连接失败，默认true
     */
    private boolean retryOnConnectionFailure = true;
}

