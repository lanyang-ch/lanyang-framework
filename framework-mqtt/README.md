# framework-mqtt

基于 Spring Integration MQTT 的消息队列通信模块，提供 MQTT 协议的消息发布和订阅功能。

## 功能特性

- ✅ 支持 MQTT 消息发布和订阅
- ✅ 支持多主题订阅和不同 QoS 级别
- ✅ 支持异步消息发送
- ✅ 提供消息异常处理机制
- ✅ 基于 Spring Boot 自动配置，开箱即用
- ✅ 支持消息头处理和动态主题指定

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 2.7.12 | 基础框架 |
| Spring Integration MQTT | - | MQTT 集成支持 |
| Eclipse Paho MQTT | - | MQTT 客户端实现 |
| Lombok | 1.18.26 | 代码简化工具 |

## 快速开始

### 1. 添加依赖

在您的项目中添加 framework-mqtt 依赖：

```xml
<dependency>
    <groupId>com.lanyang.framework</groupId>
    <artifactId>framework-mqtt</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2. 配置 MQTT 连接

在 `application.yml` 中添加 MQTT 配置：

```yaml
mqtt:
  host: tcp://localhost:1883          # MQTT 服务器地址
  clientId: lanyang-mqtt-client       # 客户端 ID
  username: admin                      # 用户名（可选）
  password: password                   # 密码（可选）
  defaultTopic: test/topic             # 默认主题
```

### 3. 启用 MQTT 配置

在启动类或配置类上添加 `@EnableConfigurationProperties` 注解：

```java
@SpringBootApplication
@EnableConfigurationProperties(MqttProperties.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

## 配置说明

### MqttProperties 配置参数

| 参数 | 类型 | 必填 | 默认值 | 说明 |
|------|------|------|--------|------|
| host | String | 是 | - | MQTT 服务器地址，格式：tcp://host:port |
| clientId | String | 是 | - | 客户端唯一标识 |
| username | String | 否 | - | MQTT 服务器用户名 |
| password | String | 否 | - | MQTT 服务器密码 |
| defaultTopic | String | 是 | - | 默认发布/订阅主题 |

### MQTT 连接参数

在 `MqttConfig` 中配置的连接参数：

- **keepAliveInterval**: 30秒 - 心跳间隔
- **connectionTimeout**: 10秒 - 连接超时时间
- **completionTimeout**: 5000毫秒 - 消息完成超时时间

## 使用示例

### 1. 发送消息

#### 发送到默认主题

```java
@Service
public class MessageService {
    
    @Autowired
    private MqttGateway mqttGateway;
    
    public void sendMessage(String message) {
        mqttGateway.send(message);
    }
}
```

#### 发送到指定主题

```java
@Service
public class MessageService {
    
    @Autowired
    private MqttGateway mqttGateway;
    
    public void sendMessageToTopic(String topic, String message) {
        mqttGateway.send(topic, message);
    }
}
```

### 2. 接收消息

#### 创建消息监听器

```java
@Component
@Slf4j
public class CustomMessageListener {
    
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(String payload, 
                            @Header("mqtt_receivedTopic") String topic) {
        log.info("收到消息 - 主题: {}, 内容: {}", topic, payload);
        
        // 根据主题进行不同的业务处理
        switch (topic) {
            case "test/topic1":
                handleTopic1(payload);
                break;
            case "test/topic2":
                handleTopic2(payload);
                break;
            default:
                handleDefaultTopic(payload);
        }
    }
    
    private void handleTopic1(String payload) {
        // 处理 topic1 的业务逻辑
    }
    
    private void handleTopic2(String payload) {
        // 处理 topic2 的业务逻辑
    }
    
    private void handleDefaultTopic(String payload) {
        // 处理默认主题的业务逻辑
    }
}
```

### 3. 异常处理

```java
@Component
@Slf4j
public class MqttErrorHandler {
    
    @ServiceActivator(inputChannel = "mqttErrorChannel")
    public void handleError(Message<?> message) {
        MessageHandlingException ex = (MessageHandlingException) message.getPayload();
        log.error("MQTT 处理异常: {}", ex.getMessage(), ex);
        
        // 根据异常类型进行不同的处理
        if (ex.getCause() instanceof MqttException) {
            handleMqttException((MqttException) ex.getCause());
        } else {
            handleGeneralException(ex);
        }
    }
    
    private void handleMqttException(MqttException ex) {
        // 处理 MQTT 特定异常
        log.error("MQTT 连接异常，错误码: {}", ex.getReasonCode());
    }
    
    private void handleGeneralException(Exception ex) {
        // 处理一般异常
        log.error("消息处理异常", ex);
    }
}
```

## API 文档

### MqttGateway 接口

消息发送网关接口，提供 MQTT 消息发送功能。

#### 方法

##### send(String payload)
发送消息到默认主题。

**参数:**
- `payload` - 消息内容

**示例:**
```java
mqttGateway.send("Hello MQTT");
```

##### send(@Header(MqttHeaders.TOPIC) String topic, String payload)
发送消息到指定主题。

**参数:**
- `topic` - 目标主题
- `payload` - 消息内容

**示例:**
```java
mqttGateway.send("custom/topic", "Hello Custom Topic");
```

## 工作原理

### 消息发送流程

```
业务代码
  ↓
MqttGateway.send()
  ↓
@MessagingGateway 转换消息
  ↓
mqttOutputChannel (输出通道)
  ↓
@ServiceActivator 监听
  ↓
MqttPahoMessageHandler
  ↓
MQTT Broker
```

### 消息接收流程

```
MQTT Broker
  ↓
MqttPahoMessageDrivenChannelAdapter (订阅主题)
  ↓
DefaultPahoMessageConverter (消息转换)
  ↓
mqttInputChannel (输入通道)
  ↓
@ServiceActivator 监听
  ↓
MqttMessageListener.receive()
  ↓
业务逻辑处理
```

### 异常处理流程

```
任何环节异常
  ↓
mqttErrorChannel (错误通道)
  ↓
@ServiceActivator 监听
  ↓
MqttMessageListener.handleError()
  ↓
异常处理和日志记录
```

## 核心组件

### 1. MqttConfig
MQTT 配置类，负责创建和配置 MQTT 相关的 Bean。

**主要功能:**
- 配置 MQTT 连接选项
- 创建 MQTT 客户端工厂
- 配置消息输入/输出通道
- 配置消息发送和接收处理器

### 2. MqttProperties
MQTT 配置属性类，从配置文件中读取 MQTT 连接参数。

**配置属性:**
- host: MQTT 服务器地址
- clientId: 客户端 ID
- username: 用户名
- password: 密码
- defaultTopic: 默认主题

### 3. MqttGateway
MQTT 消息发送网关接口，提供消息发送功能。

**方法:**
- send(String payload): 发送到默认主题
- send(String topic, String payload): 发送到指定主题

### 4. MqttMessageListener
MQTT 消息监听器，处理接收到的 MQTT 消息。

**方法:**
- receive(String payload, String topic): 接收并处理消息
- handleError(Message<?> message): 处理 MQTT 异常

## MQTT QoS 级别

MQTT 提供三种消息服务质量级别：

| QoS 级别 | 名称 | 说明 | 使用场景 |
|----------|------|------|----------|
| 0 | 最多一次 | 消息可能丢失或重复 | 不重要的消息，如传感器数据 |
| 1 | 至少一次 | 消息保证到达，可能重复 | 重要消息，可接受重复 |
| 2 | 恰好一次 | 消息保证到达且不重复 | 关键消息，不能重复 |

**配置示例:**

在 `MqttConfig` 中配置不同主题的 QoS：

```java
Map<String, Integer> topicQosMap = new HashMap<>();
topicQosMap.put("sensor/data", 0);  // 传感器数据，允许丢失
topicQosMap.put("alarm/alert", 1);  // 告警消息，至少一次
topicQosMap.put("control/command", 2);  // 控制命令，恰好一次
topicQosMap.forEach(adapter::addTopic);
```

## 最佳实践

### 1. 连接管理

- **合理设置心跳间隔**: 根据网络环境调整 `keepAliveInterval`
- **设置连接超时**: 配置合理的 `connectionTimeout` 避免长时间等待
- **客户端 ID 唯一**: 确保每个客户端使用唯一的 clientId

### 2. 消息处理

- **异步发送**: 使用异步发送提高性能
- **消息验证**: 对接收的消息进行格式和内容验证
- **异常处理**: 完善异常处理机制，确保系统稳定性

### 3. 主题设计

- **层级结构**: 使用 `/` 分隔符创建有意义的主题层级
- **通配符使用**: 合理使用 `+` 和 `#` 通配符订阅多个主题
- **主题长度**: 避免过长的主题名称，影响性能

### 4. 性能优化

- **批量处理**: 对于高频消息，考虑批量处理
- **消息压缩**: 对于大消息，考虑使用压缩
- **连接池**: 对于高并发场景，考虑使用连接池

## 常见问题

### 1. 连接失败

**问题**: 无法连接到 MQTT 服务器

**解决方案**:
- 检查 MQTT 服务器地址和端口是否正确
- 确认网络连接是否正常
- 验证用户名和密码是否正确
- 检查防火墙设置

### 2. 消息丢失

**问题**: 发送的消息未收到

**解决方案**:
- 使用 QoS 1 或 QoS 2 确保消息可靠传输
- 检查主题名称是否正确
- 确认订阅的主题与发布的主题匹配
- 检查消息监听器是否正常工作

### 3. 性能问题

**问题**: 消息处理性能不佳

**解决方案**:
- 使用异步发送提高性能
- 优化消息处理逻辑
- 考虑使用消息队列进行缓冲
- 增加 MQTT 服务器资源

### 4. 重复消息

**问题**: 收到重复的消息

**解决方案**:
- 使用 QoS 2 确保消息不重复
- 在业务层实现消息去重逻辑
- 使用消息 ID 进行去重

## 示例项目

完整的示例代码请参考项目中的 `MqttTestController`：

```java
@RestController
@RequestMapping("/mqtt")
public class MqttTestController {
    
    @Autowired
    private MqttGateway mqttGateway;
    
    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        mqttGateway.send(message);
        return "消息发送成功";
    }
    
    @PostMapping("/send/{topic}")
    public String sendMessageToTopic(
            @PathVariable String topic, 
            @RequestParam String message) {
        mqttGateway.send(topic, message);
        return "消息发送到主题 " + topic + " 成功";
    }
}
```

## 版本历史

### v1.0.0 (2026-04-03)
- 初始版本发布
- 支持基本的 MQTT 消息发布和订阅
- 支持多主题订阅
- 支持异常处理

## 许可证

本项目采用 Apache License 2.0 许可证。详见 [LICENSE](../LICENSE) 文件。

## 贡献

欢迎贡献代码！请遵循以下步骤：

1. Fork 本项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

## 联系方式

- 项目地址: [lanyang-framework](https://github.com/yourusername/lanyang-framework)
- 问题反馈: [Issues](https://github.com/yourusername/lanyang-framework/issues)

## 相关文档

- [MQTT 协议规范](http://mqtt.org/)
- [Spring Integration MQTT 文档](https://docs.spring.io/spring-integration/reference/html/mqtt.html)
- [Eclipse Paho MQTT 客户端](https://www.eclipse.org/paho/)
