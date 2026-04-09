package com.lanyang.framework.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @author lanyang
 * @date 2026/4/3
 * @des
 */
@Slf4j
@Component
public class MqttMessageListener {

    /**
     * 接收MQTT消息
     * @param payload
     * @param topic
     */
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void receive(String payload, @Header("mqtt_receivedTopic") String topic) {
        log.info("收到MQTT消息 | topic:{} | payload:{}", topic, payload);
        // 这里写你的业务逻辑
    }

    /**
     * 处理MQTT异常
     * @param message
     */
    @ServiceActivator(inputChannel = "mqttErrorChannel")
    public void handleError(Message<?> message) {
        MessageHandlingException ex = (MessageHandlingException) message.getPayload();
        log.error("MQTT异常: {}", ex.getMessage(), ex);
    }
}
