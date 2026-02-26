# framework-netty 模块

## 模块简介

framework-netty 是一个基于 Netty 实现的网络通信框架模块，提供了简单易用的 TCP 服务器功能，支持 JSON 格式的数据传输。

## 功能特性

- ✅ 自动配置 Netty 服务器
- ✅ 支持 JSON 格式数据解码
- ✅ 可配置的线程池和心跳机制
- ✅ 支持自定义通道处理器
- ✅ 提供 Spring Boot 集成

## 目录结构

```
framework-netty/
├── src/main/java/com/lanyang/framework/netty/
│   ├── annotation/           # 注解定义
│   │   └── EnableNettyConfig.java  # 启用 Netty 配置注解
│   ├── codec/                # 编解码器
│   │   ├── SocketDecoder.java      # 套接字解码器
│   │   └── SocketEncoder.java      # 套接字编码器
│   ├── config/               # 配置类
│   │   ├── NettyConfig.java        # Netty 服务器配置
│   │   └── NettyProperties.java    # Netty 配置属性
│   ├── handler/              # 处理器
│   │   └── NettyChannelHandler.java  # 通道处理器接口
│   ├── initializer/          # 初始化器
│   │   ├── NettyInitializer.java    # 通道初始化器
│   │   └── NettyServer.java         # Netty 服务器
│   └── runner/               # 运行器
│       └── NettyServerRunner.java   # 服务器运行器
├── pom.xml                   # Maven 依赖配置
└── README.md                 # 模块说明文档
```

## 核心组件

### 1. NettyProperties

配置属性类，用于管理 Netty 服务器的各项配置参数。

**主要配置项：**
- `port`：服务器端口号，默认 18050
- `bossCount`：Boss 线程数，默认 1
- `workerCount`：Worker 线程数，默认 50
- `keepalive`：是否保持连接，默认 true
- `backlog`：连接队列大小，默认 100
- `heartbeat`：心跳周期（秒），默认 2
- `delay`：定时器延时启动时间（秒），默认 35
- `readPeriod`：采集周期（分钟），默认 15
- `jsonDecoder`：是否使用 JSON 解码器，默认 true
- `maxObjectLength`：JSON 数据包最大长度，默认 5120（5KB）

### 2. NettyConfig

Netty 服务器配置类，负责创建和配置 Netty 服务器的各个组件。

**主要功能：**
- 创建 ServerBootstrap 实例
- 配置 BossGroup 和 WorkerGroup 线程池
- 设置 TCP 通道选项

### 3. NettyInitializer

通道初始化器，负责配置每个客户端连接的通道 pipeline。

**主要功能：**
- 添加 JSON 解码器（如果启用）
- 添加字符串编解码器
- 添加自定义通道处理器

### 4. NettyServer

Netty 服务器类，负责启动和停止 Netty 服务器。

**主要方法：**
- `start()`：启动服务器并绑定端口
- `stop()`：停止服务器并关闭通道

### 5. NettyServerRunner

服务器运行器，实现了 Spring Boot 的 ApplicationRunner 接口，在应用启动后自动启动 Netty 服务器。

### 6. EnableNettyConfig

启用 Netty 配置的注解，用于在 Spring Boot 应用中启用 Netty 服务器。

## 快速开始

### 1. 添加依赖

在项目的 pom.xml 文件中添加 framework-netty 模块依赖：

```xml
<dependency>
    <groupId>com.lanyang.framework</groupId>
    <artifactId>framework-netty</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2. 启用 Netty 配置

在 Spring Boot 应用的主配置类上添加 `@EnableNettyConfig` 注解：

```java
@SpringBootApplication
@EnableNettyConfig
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### 3. 配置 Netty 属性

在 application.yml 文件中配置 Netty 相关属性：

```yaml
netty:
  port: 18050                    # 服务器端口
  bossCount: 1                    # Boss 线程数
  workerCount: 50                 # Worker 线程数
  keepalive: true                 # 是否保持连接
  backlog: 100                    # 连接队列大小
  heartbeat: 2                    # 心跳周期（秒）
  delay: 35                       # 定时器延时启动时间（秒）
  readPeriod: 15                  # 采集周期（分钟）
  jsonDecoder: true               # 是否使用 JSON 解码器
  maxObjectLength: 5120           # JSON 数据包最大长度（字节）
```

### 4. 实现自定义通道处理器

创建一个类实现 `NettyChannelHandler` 接口，处理客户端消息：

```java
@Component
@Slf4j
@RequiredArgsConstructor
public class MyChannelHandler extends NettyChannelHandler {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("Netty客户端连接: {}", ctx.channel().remoteAddress().toString());
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String remoteAddress = ctx.channel().remoteAddress().toString();
        log.info("Netty客户端断开连接: {}", remoteAddress);
        ctx.fireChannelInactive();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String message) throws Exception {
        log.info("收到Netty消息: {}", message);

        //upStreamHandlerStrategy.handle(channelHandlerContext, cleanedMessage);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Netty exception", cause);
        //ChannelHandlerContextHolder.getChannelHashMap().remove(ctx);
        ctx.close();
    }
}
```

## 工作原理

1. **启动流程**：
   - 应用启动时，`NettyServerRunner` 自动执行
   - 调用 `NettyServer.start()` 方法启动服务器
   - `NettyServer` 使用 `ServerBootstrap` 绑定端口

2. **连接处理**：
   - 客户端连接时，`NettyInitializer` 初始化通道
   - 根据配置添加相应的解码器和处理器
   - 自定义 `NettyChannelHandler` 处理业务逻辑

3. **消息处理**：
   - 客户端发送消息后，经过解码器解码
   - 解码后的消息传递给自定义处理器
   - 处理器处理消息并回复客户端

## 注意事项

1. **线程安全**：Netty 是基于事件驱动的，所有的 ChannelHandler 都会在 EventLoop 线程中执行，确保线程安全

2. **资源管理**：Netty 会自动管理连接和通道资源，但在自定义处理器中要注意释放资源

3. **性能优化**：
   - 根据实际需求调整 `workerCount` 线程数
   - 合理设置 `backlog` 和 `maxObjectLength` 参数
   - 对于耗时操作，建议使用 `EventExecutorGroup` 线程池

4. **异常处理**：在自定义处理器中要妥善处理异常，避免影响整个服务器

## 依赖说明

- **Spring Boot**：提供自动配置和依赖注入
- **Netty**：提供网络通信核心功能
- **Lombok**：简化代码开发
- **Apache Commons Collections**：提供集合工具类

## 常见问题

### Q: 如何自定义消息解码器？

A: 可以实现自定义的 ChannelHandler 并添加到 pipeline 中，替换默认的 JSON 解码器。

### Q: 如何处理大文件传输？

A: 可以调整 `maxObjectLength` 参数，或实现分块传输机制。

### Q: 如何添加 SSL 支持？

A: 可以在 `NettyInitializer` 中添加 SSLHandler 到 pipeline 中。

### Q: 如何监控服务器状态？

A: 可以通过 Spring Boot Actuator 或自定义监控指标来监控服务器状态。

## 版本历史

- **1.0.0**：初始版本，提供基本的 Netty 服务器功能

## 许可证

本模块采用 MIT 许可证，详见项目根目录的 LICENSE 文件。