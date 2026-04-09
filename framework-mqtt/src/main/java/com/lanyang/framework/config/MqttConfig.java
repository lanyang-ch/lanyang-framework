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


    /**
     * mqtt 连接配置
     * @param mqttProperties
     * @return
     */
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

    /**
     * mqtt 客户端工厂
     * @param mqttConnectOptions
     * @return
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory(MqttConnectOptions mqttConnectOptions) {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(mqttConnectOptions);
        return factory;
    }

    /**
     * 输入通道
     * inbound() 适配器把消息丢进这个通道
     * @return
     */
    @Bean
    public MessageChannel mqttInputChannel() {
        DirectChannel mqttInputChannel = new DirectChannel();
        return mqttInputChannel;
    }

    /**
     * 输入适配器
     * 创建一个一直运行的 MQTT 监听客户端，负责：连接、订阅、收消息
     * 用 mqttClientFactory 创建连接
     * 订阅多个 Topic，每个 Topic 可设置不同 QoS
     * 收到消息 → 丢给 mqttInputChannel
     * 出错 → 丢给 mqttErrorChannel
     * @param mqttProperties
     * @param mqttConnectOptions
     * @return
     */
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

    /**
     * 出站消息通道
     * 业务代码 → Gateway → 此通道 → 发送处理器
     * MqttGateway 往这里发消息
     * outbound() 处理器从这里取消息
     * @return
     */
    @Bean
    public MessageChannel mqttOutputChannel() {
        DirectChannel mqttOutputChannel = new DirectChannel();
        return mqttOutputChannel;
    }

    /**
     * 真正发送 MQTT 消息的组件，MqttGateway 发出的消息最终由它发送到 Broker
     * @ServiceActivator：监听 mqttOutputChannel
     * 通道一有消息，就调用它发送
     * 发送时可动态指定 topic、qos
     * 不需要默认 topic
     *
     * @param mqttProperties
     * @param mqttConnectOptions
     * @return
     */
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


    /**
     * 错误通道
     * inbound 出错 → 此通道 → 异常处理方法
     * @return
     */
    @Bean
    public MessageChannel mqttErrorChannel() {
        return new DirectChannel();
    }
}
