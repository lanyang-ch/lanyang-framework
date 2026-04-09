package com.lanyang.framework.handler;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * @author lanyang
 * @date 2026/4/3
 * @des
 */
@MessagingGateway(defaultRequestChannel = "mqttOutputChannel")
public interface MqttGateway {
    // 发送到默认主题
    void send(String payload);

    // 发送到指定主题
    void send(@Header(MqttHeaders.TOPIC) String topic, String payload);
}
