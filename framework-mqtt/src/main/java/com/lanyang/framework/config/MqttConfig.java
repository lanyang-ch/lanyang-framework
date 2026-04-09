package com.lanyang.framework.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lanyang
 * @date 2026/4/3
 * @des
 */
@Configuration
@EnableConfigurationProperties(MqttProperties.class)
public class MqttConfig {

//    private final Map<String, Integer> topicQosMap = Map.of(
//            "test/topic1", 0,
//            "test/topic2", 1,
//            "test/topic3", 2
//    );


    @Bean
    public MqttConnectOptions mqttConnectOptions(MqttProperties mqttProperties) {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName(mqttProperties.getUsername());
        mqttConnectOptions.setPassword(mqttProperties.getPassword().toCharArray());
        mqttConnectOptions.setServerURIs(new String[]{mqttProperties.getHost()});
        mqttConnectOptions.setKeepAliveInterval(30);
        mqttConnectOptions.setConnectionTimeout(10);
        return mqttConnectOptions;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory(MqttConnectOptions mqttConnectOptions) {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(mqttConnectOptions);
        return factory;
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        DirectChannel mqttInputChannel = new DirectChannel();
        return mqttInputChannel;
    }

    // 配置入站适配器：监听主题
    @Bean
    public MessageProducer inbound(MqttProperties mqttProperties, MqttConnectOptions mqttConnectOptions) {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        mqttProperties.getClientId(),
                        mqttClientFactory(mqttConnectOptions),
                        mqttProperties.getDefaultTopic()); // 可写多个主题

        // 多主题 + 各自 QoS
        Map<String, Integer> topicQosMap = new HashMap<>();
        topicQosMap.put("test/topic1", 0);
        topicQosMap.put("test/topic2", 1);
        topicQosMap.forEach(adapter::addTopic);

        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        adapter.setErrorChannel(mqttErrorChannel()); // 异常通道
        return adapter;
    }

    // ==========================
    // 出站通道（发送消息）
    // ==========================
    @Bean
    public MessageChannel mqttOutputChannel() {
        DirectChannel mqttOutputChannel = new DirectChannel();
        return mqttOutputChannel;
    }

    // 出站适配器
    @Bean
    @ServiceActivator(inputChannel = "mqttOutputChannel")
    public MessageHandler outbound(MqttProperties mqttProperties, MqttConnectOptions mqttConnectOptions) {
        MqttPahoMessageHandler handler =
                new MqttPahoMessageHandler(
                        mqttProperties.getClientId() + "_out",
                        mqttClientFactory(mqttConnectOptions));
        handler.setAsync(true);
        handler.setDefaultTopic(mqttProperties.getDefaultTopic());
        handler.setDefaultQos(1);
        return handler;
    }


    // ==========================
    // 异常通道
    // ==========================
    @Bean
    public MessageChannel mqttErrorChannel() {
        return new DirectChannel();
    }
}
