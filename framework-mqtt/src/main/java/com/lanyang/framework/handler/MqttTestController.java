package com.lanyang.framework.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lanyang
 * @date 2026/4/3
 * @des
 */
@RestController
@RequiredArgsConstructor
public class MqttTestController {

    private final MqttGateway mqttGateway;

    @GetMapping("/mqtt/send/{msg}")
    public String send(@PathVariable String msg) {
        mqttGateway.send(msg);
        return "发送成功：" + msg;
    }

    @GetMapping("/mqtt/send/{topic}/{msg}")
    public String sendToTopic(@PathVariable String topic, @PathVariable String msg) {
        mqttGateway.send(topic, msg);
        return "发送到主题[" + topic + "]成功：" + msg;
    }
}
