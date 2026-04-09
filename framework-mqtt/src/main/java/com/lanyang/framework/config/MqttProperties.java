package com.lanyang.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lanyang
 * @date 2026/4/3
 * @des
 */
@Data
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {

    private String host;

    private String clientId;

    private String username;

    private String password;

    private String defaultTopic;
}
